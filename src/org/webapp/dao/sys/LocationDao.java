package org.webapp.dao.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.glibs.database.SqlBuilder;
import org.glibs.database.SqlUtil;

public class LocationDao {
	public static Map<String, Object> getOne(Integer locationId) {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_Location");

		s.addField("locationId");
		s.addField("cnName");
		s.addField("enName");
		s.addField("locationNo");
		s.addField("parentNo");
		s.addField("levelCnName");
		s.addField("levelEnName");
		s.addField("isLeaf");

		s.addWhere("", "location", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(locationId);

		return new SqlUtil().getMap(s.select(), params);
	}

	public static List<Map<String, Object>> getList() {
		return getList("");
	}

	public static List<Map<String, Object>> getList(String parentNo) {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_Location");

		s.addField("locationId");
		s.addField("cnName");
		s.addField("enName");
		s.addField("locationNo");
		s.addField("parentNo");
		s.addField("levelCnName");
		s.addField("levelEnName");
		s.addField("isLeaf");

		List<Object> params = new ArrayList<Object>();

		if (!parentNo.isEmpty()) {
			s.addWhere("", "locationNo", "like", "?||'%'");
			params.add(parentNo);
		}

		s.addOrderBy("locationNo", true);

		return new SqlUtil().getList(s.select(), params);
	}

	public static List<Map<String, Object>> getList(LocationType lType) {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_Location");

		s.addField("locationId");
		s.addField("cnName");
		s.addField("enName");
		s.addField("locationNo");
		s.addField("parentNo");
		s.addField("levelCnName");
		s.addField("levelEnName");
		s.addField("isLeaf");

		s.addWhere("", "levelEnName", "=", "?");

		s.addOrderBy("locationNo", true);

		List<Object> params = new ArrayList<Object>();
		params.add(lType.toString());

		return new SqlUtil().getList(s.select(), params);
	}

	public static Long insert(Map<String, Object> content) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_Location");

		s.addField("isLeaf");

		s.addWhere("", "locationNo", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(false);
		params.add(content.get("parentNo"));

		new SqlUtil().update(s.update(), params);

		s = new SqlBuilder();

		s.addTable("Sys_Location");

		s.addField("cnName");
		s.addField("enName");
		s.addField("locationNo");
		s.addField("parentNo");
		s.addField("levelCnName");
		s.addField("levelEnName");
		s.addField("isLeaf");

		s.setSequence("Sys_Location", "locationId");

		params = new ArrayList<Object>();
		params.add(content.get("cnName"));
		params.add(content.get("enName"));
		params.add(content.get("locationNo"));
		params.add(content.get("parentNo"));
		params.add(content.get("levelCnName"));
		params.add(content.get("levelEnName"));
		params.add(true);

		return new SqlUtil().insert(s.insert(), params, s.sequence());
	}
	
	
}
