package org.glibs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlUtil {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public SqlUtil() {

	}

	private void openDB() {
		try {
			Class.forName("org.postgresql.Driver");
			this.conn = DriverManager.getConnection(
					"jdbc:postgresql://192.168.56.2:5432/WebData", "postgres",
					"10018583");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void closeDB() {
		try {
			this.pstmt.close();
			this.conn.close();
		} catch (SQLException e) {
			this.pstmt = null;
			this.conn = null;
			e.printStackTrace();
		}
	}

	private void buildStatement(List<Object> params) {
		try {
			if (params.size() > 0) {
				for (int i = 0, j = params.size(); i < j; i++) {
					this.pstmt.setObject(i + 1, params.get(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Map<String, Object>> buildList(ResultSet rs) {
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			ResultSetMetaData rsmd = rs.getMetaData();

			String columnName = "";
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					columnName = rsmd.getColumnLabel(i);
					map.put(columnName, rs.getObject(columnName));
				}
				list.add(map);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	private Map<String, Object> buildMap(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<String, Object> map = new HashMap<String, Object>();

			rs.next();
			if (rsmd.getColumnCount() > 0) {
				String columnName = "";
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					columnName = rsmd.getColumnLabel(i);
					map.put(columnName, rs.getObject(columnName));
				}
			}

			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	public Long insert(String sql, List<Object> params, String seq) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			this.pstmt.executeUpdate();

			this.pstmt = this.conn.prepareStatement(seq);
			this.rs = this.pstmt.executeQuery();

			this.conn.commit();

			Long l = 0L;
			if (this.rs.next()) {
				l = this.rs.getLong(1);
			}

			return l;

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				this.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			return 0L;

		} finally {
			this.closeDB();
		}
	}

	public Integer update(String sql, List<Object> params) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			int w = this.pstmt.executeUpdate();
			this.conn.commit();
			return w;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				this.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			this.closeDB();
		}
	}

	public Integer batch(String sql, List<List<Object>> paramsList) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);

			for (List<Object> params : paramsList) {
				this.buildStatement(params);
				this.pstmt.addBatch();
			}

			int[] w = this.pstmt.executeBatch();
			this.conn.commit();
			return w.length;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				this.conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			this.closeDB();
		}
	}

	public List<Map<String, Object>> getList(String sql, List<Object> params) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			this.rs = this.pstmt.executeQuery();
			this.conn.commit();
			return this.buildList(this.rs);
		} catch (SQLException ex) {
			return null;
		} finally {
			this.closeDB();
		}
	}

	public Map<String, Object> getMap(String sql, List<Object> params) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			this.rs = this.pstmt.executeQuery();
			this.conn.commit();
			return this.buildMap(this.rs);
		} catch (SQLException ex) {
			return null;
		} finally {
			this.closeDB();
		}
	}

	public Object getValue(String sql, List<Object> params) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			this.rs = this.pstmt.executeQuery();

			Object v = null;
			if (this.rs.next()) {
				v = this.rs.getObject(1);
			}

			this.conn.commit();

			return v;
		} catch (SQLException ex) {
			return null;
		} finally {
			this.closeDB();
		}
	}

	public String getValueString(String sql, List<Object> params) {
		try {
			this.openDB();
			this.conn.setAutoCommit(false);
			this.pstmt = this.conn.prepareStatement(sql);
			this.buildStatement(params);
			this.rs = this.pstmt.executeQuery();

			StringBuffer s = new StringBuffer();

			while (this.rs.next()) {
				s.append(",");
				s.append(this.rs.getObject(1));
			}

			this.conn.commit();

			return s.length() > 0 ? s.substring(1) : "";
		} catch (SQLException ex) {
			return null;
		} finally {
			this.closeDB();
		}
	}
}
