package org.glibs.database;

import java.util.ArrayList;
import java.util.List;

public class SqlBuilder {
	private String tagField;
	private StringBuffer tables;

	private List<String> fieldNames;
	private List<String> shortNames;

	private StringBuffer wheres;
	private StringBuffer orderBys;

	private String sequence;

	public SqlBuilder() {
		this.clearBuilder();
	}

	public void clearBuilder() {
		this.tables = new StringBuffer();

		this.fieldNames = new ArrayList<String>();
		this.shortNames = new ArrayList<String>();

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
		this.tables.append(this.getString(tableName));
	}

	public void addTable(String tableName, String shortName) {
		this.tables
				.append("," + this.getString(tableName) + " as " + shortName);
	}

	public void clearTables() {
		this.tables = new StringBuffer();
	}

	private String getTables() {
		return this.tables.toString().substring(1);
	}

	public void addField(String fieldName) {
		this.fieldNames.add(fieldName);
	}

	public void addField(String shortName, String fieldName) {
		this.fieldNames.add(fieldName);
		this.shortNames.add(shortName);
	}

	public void clearFields() {
		this.fieldNames = new ArrayList<String>();
		this.shortNames = new ArrayList<String>();
	}

	private String getFields() {
		if (this.fieldNames.size() > 0) {
			StringBuffer s = new StringBuffer();

			for (int i = 0, j = this.fieldNames.size(); i < j; i++) {
				s.append(",");

				if (this.shortNames.size() == this.fieldNames.size()) {
					s.append(this.shortNames.get(i));
					s.append(".");
				}

				s.append(this.getString(this.fieldNames.get(i)));
			}

			return s.toString().substring(1);
		} else {
			return "*";
		}
	}

	private String getFields(Boolean isUpdate) {
		if (this.fieldNames.size() > 0) {
			if (isUpdate) {
				StringBuffer s = new StringBuffer();

				for (int i = 0, j = this.fieldNames.size(); i < j; i++) {
					s.append(",");
					s.append(this.getString(this.fieldNames.get(i)));
					s.append("=?");
				}

				return s.toString().substring(1);
			} else {
				StringBuffer s = new StringBuffer();
				StringBuffer v = new StringBuffer();

				for (int i = 0, j = this.fieldNames.size(); i < j; i++) {
					s.append(",");
					s.append(this.getString(this.fieldNames.get(i)));

					v.append(",?");
				}

				return "(" + s.toString().substring(1) + ")values("
						+ v.toString().substring(1) + ")";
			}
		} else {
			return "";
		}
	}

	public void addOrderBy(String fieldName, Boolean isAsc) {
		this.orderBys.append("," + this.getString(fieldName) + " ");

		if (isAsc) {
			this.orderBys.append("asc");
		} else {
			this.orderBys.append("desc");
		}
	}

	public void addOrderBy(String shortName, String fieldName, Boolean isAsc) {
		this.orderBys.append("," + shortName + "." + this.getString(fieldName)
				+ " ");

		if (isAsc) {
			this.orderBys.append("asc");
		} else {
			this.orderBys.append("desc");
		}
	}

	public void clearOrderBy() {
		this.orderBys = new StringBuffer();
	}

	private String getOrderBy() {
		return this.orderBys.length() > 0 ? this.orderBys.toString().substring(
				1) : "";
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

	private String getWhere() {
		return "where" + this.wheres.toString();
	}

	public String select() {
		StringBuffer s = new StringBuffer();

		s.append("select ");
		s.append(this.getFields());
		s.append(" from ");
		s.append(this.getTables());

		if (this.wheres.length() > 0) {
			s.append(" ");
			s.append(this.getWhere());
		}

		if (this.orderBys.length() > 0) {
			s.append(" ");
			s.append(this.getOrderBy());
		}

		return s.toString();
	}

	public String page() {
		StringBuffer s = new StringBuffer();

		s.append("select ");
		s.append(this.getFields());
		s.append(" from ");
		s.append(this.getTables());

		if (this.wheres.length() > 0) {
			s.append(" ");
			s.append(this.getWhere());
		}

		if (this.orderBys.length() > 0) {
			s.append(" ");
			s.append(this.getOrderBy());
		}

		s.append(" limit ? offset ?");

		return s.toString();
	}

	public String insert() {
		StringBuffer s = new StringBuffer();

		s.append("insert into ");
		s.append(this.getTables());
		s.append(" ");
		s.append(this.getFields(false));

		return s.toString();
	}

	public String update() {
		StringBuffer s = new StringBuffer();

		s.append("update ");
		s.append(this.getTables());
		s.append(" set ");
		s.append(this.getFields(true));
		s.append(" ");
		s.append(this.getWhere());

		return s.toString();
	}

	public String delete() {
		StringBuffer s = new StringBuffer();

		s.append("delete from ");
		s.append(this.getTables());

		if (this.wheres.length() > 0) {
			s.append(" ");
			s.append(this.getWhere());
		}

		return s.toString();
	}

	public String count() {
		StringBuffer s = new StringBuffer();

		s.append("select count(");
		s.append(this.tagField);
		s.append(") from ");
		s.append(this.getTables());

		if (this.wheres.length() > 0) {
			s.append(" ");
			s.append(this.getWhere());
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
