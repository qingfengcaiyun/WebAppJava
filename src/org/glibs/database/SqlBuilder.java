package org.glibs.database;

public class SqlBuilder {
	private String tagField;
	private StringBuffer tables;
	private StringBuffer fields;
	private StringBuffer wheres;
	private StringBuffer orderBys;
	private String sequence;

	public SqlBuilder() {
		this.clearBuilder();
	}

	public void clearBuilder() {
		this.tables = new StringBuffer();
		this.fields = new StringBuffer();
		this.wheres = new StringBuffer();
		this.orderBys = new StringBuffer();
		this.tagField = "";
		this.sequence = "";
	}

	public void setSequence(String tableName, String pkName) {
		this.sequence = tableName + "_" + pkName + "_seq";
	}

	public void setTagField(String fieldName) {
		this.tagField = this.getString(fieldName);
	}

	public void setTagField(String shortName, String fieldName) {
		this.tagField = shortName + "." + this.getString(fieldName);
	}

	public void clearTagField() {
		this.tagField = "";
	}

	public void addTable(String tableName) {
		if (this.tables.length() > 0) {
			this.tables = new StringBuffer();
		}
		this.tables.append(this.getString(tableName));
	}

	public void addTable(String tableName, String shortName) {
		if (this.tables.length() > 0) {
			this.tables.append(",");
		}
		this.tables.append(this.getString(tableName) + " as " + shortName);
	}

	public void clearTables() {
		this.tables = new StringBuffer();
	}

	public void addField(String fieldName) {
		if (this.fields.length() > 0) {
			this.fields.append(",");
		}
		this.fields.append(this.getString(fieldName));
	}

	public void addField(String shortName, String fieldName) {
		if (this.fields.length() > 0) {
			this.fields.append(",");
		}
		this.fields.append(shortName + "." + this.getString(fieldName));
	}

	public void addField(String shortName, String fieldName, String nickName) {
		if (this.fields.length() > 0) {
			this.fields.append(",");
		}
		this.fields.append(shortName + "." + this.getString(fieldName) + " as "
				+ nickName);
	}

	public void clearFields() {
		this.fields = new StringBuffer();
	}

	public void addOrderBy(String fieldName, Boolean isAsc) {
		if (this.orderBys.length() > 0) {
			this.orderBys.append(",");
		}

		this.orderBys.append(this.getString(fieldName) + " ");

		if (isAsc) {
			this.orderBys.append("asc");
		} else {
			this.orderBys.append("desc");
		}
	}

	public void addOrderBy(String shortName, String fieldName, Boolean isAsc) {
		if (this.orderBys.length() > 0) {
			this.orderBys.append(",");
		}

		this.orderBys.append(shortName + "." + this.getString(fieldName) + " ");

		if (isAsc) {
			this.orderBys.append("asc");
		} else {
			this.orderBys.append("desc");
		}
	}

	public void clearOrderBy() {
		this.orderBys = new StringBuffer();
	}

	public void addWhere(String relation, String leftField, String equals,
			String rightField) {

		this.wheres.append(" ");

		if (!relation.isEmpty()) {
			this.wheres.append(relation);
			this.wheres.append(" ");
		}

		this.wheres.append(this.getString(leftField));
		this.wheres.append(" ");
		this.wheres.append(equals);
		this.wheres.append(" ");
		this.wheres.append(this.getString(rightField));
	}

	public void addWhere(String relation, String leftShort, String leftField,
			String equals, String value) {
		this.wheres.append(" ");

		if (!relation.isEmpty()) {
			this.wheres.append(relation);
			this.wheres.append(" ");
		}

		if (!leftShort.isEmpty()) {
			this.wheres.append(leftShort);
			this.wheres.append(".");
		}

		this.wheres.append(this.getString(leftField));
		this.wheres.append(" ");
		this.wheres.append(equals);
		this.wheres.append(" ");
		this.wheres.append(value);
	}

	public void addWhere(String relation, String leftShort, String leftField,
			String equals, String rightShort, String rightField) {
		this.wheres.append(" ");

		if (!relation.isEmpty()) {
			this.wheres.append(relation);
			this.wheres.append(" ");
		}

		this.wheres.append(leftShort);
		this.wheres.append(".");
		this.wheres.append(this.getString(leftField));
		this.wheres.append(" ");
		this.wheres.append(equals);
		this.wheres.append(" ");
		this.wheres.append(rightShort);
		this.wheres.append(".");
		this.wheres.append(this.getString(rightField));
	}

	public void clearWhere() {
		this.wheres = new StringBuffer();
	}

	public String select() {
		StringBuffer s = new StringBuffer();

		s.append("select ");
		s.append(this.fields);
		s.append(" from ");
		s.append(this.tables);

		if (this.wheres.length() > 0) {
			s.append(" where ");
			s.append(this.wheres);
		}

		if (this.orderBys.length() > 0) {
			s.append(" order by ");
			s.append(this.orderBys);
		}

		return s.toString();
	}

	public String page() {
		StringBuffer s = new StringBuffer();

		s.append("select ");
		s.append(this.fields);
		s.append(" from ");
		s.append(this.tables);

		if (this.wheres.length() > 0) {
			s.append(" where ");
			s.append(this.wheres);
		}

		if (this.orderBys.length() > 0) {
			s.append(" order by ");
			s.append(this.orderBys);
		}

		s.append(" limit ? offset ?");

		return s.toString();
	}

	public String insert() {
		StringBuffer v = new StringBuffer();
		int cnt = this.fields.toString().split(",").length;
		for (int i = 0; i < cnt; i++) {
			if (i > 0) {
				v.append(",");
			}
			v.append("?");
		}

		StringBuffer s = new StringBuffer();

		s.append("insert into ");
		s.append(this.tables);
		s.append(" (");
		s.append(this.fields);
		s.append(")values(");
		s.append(v);
		s.append(")");

		return s.toString();
	}

	public String update() {
		StringBuffer s = new StringBuffer();

		s.append("update ");
		s.append(this.tables);
		s.append(" set ");
		s.append(this.fields.append("=?").toString().replaceAll(",", "=?,"));

		if (this.wheres.length() > 0) {
			s.append(" where ");
			s.append(this.wheres);
		}
		return s.toString();
	}

	public String delete() {
		StringBuffer s = new StringBuffer();

		s.append("delete from ");
		s.append(this.tables);

		if (this.wheres.length() > 0) {
			s.append(" where ");
			s.append(this.wheres);
		}

		return s.toString();
	}

	public String count() {
		StringBuffer s = new StringBuffer();

		s.append("select count(");
		s.append(this.tagField);
		s.append(") from ");
		s.append(this.tables);

		if (this.wheres.length() > 0) {
			s.append(" where ");
			s.append(this.wheres);
		}

		return s.toString();
	}

	public String sequence() {
		StringBuffer s = new StringBuffer();

		s.append("select currval('");
		s.append(this.getString(this.sequence));
		s.append("')");

		return s.toString();
	}

	private String getString(String field) {
		return "\"" + field + "\"";
	}
}
