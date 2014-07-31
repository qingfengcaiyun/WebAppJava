package org.webapp.dao.sys;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.glibs.database.PageRecords;
import org.glibs.database.SqlBuilder;
import org.glibs.database.SqlUtil;
import org.glibs.text.Tools;

public class UserDao {
	public static Map<String, Object> getOne(Integer userId) {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User", "u");
		s.addTable("Sys_Location", "l");

		s.addField("l", "cnName");

		s.addField("u", "userId");
		s.addField("u", "userName");
		s.addField("u", "userPwd");
		s.addField("u", "md5Pwd");
		s.addField("u", "userType");
		s.addField("u", "lastLogin");
		s.addField("u", "locationId");
		s.addField("u", "isDeleted");
		s.addField("u", "isLocked");
		s.addField("u", "insertTime");
		s.addField("u", "updateTime");

		s.addWhere("", "l", "locationId", "=", "u", "locationId");
		s.addWhere("and", "u", "userId", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		return new SqlUtil().getMap(s.select(), params);
	}

	public static Map<String, Object> getOne(String userName, String userPwd) {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User", "u");
		s.addTable("Sys_Location", "l");

		s.addField("l", "cnName");

		s.addField("u", "userId");
		s.addField("u", "userName");
		s.addField("u", "userPwd");
		s.addField("u", "md5Pwd");
		s.addField("u", "userType");
		s.addField("u", "lastLogin");
		s.addField("u", "locationId");
		s.addField("u", "isDeleted");
		s.addField("u", "isLocked");
		s.addField("u", "insertTime");
		s.addField("u", "updateTime");

		s.addWhere("", "l", "locationId", "=", "u", "locationId");
		s.addWhere("and", "u", "userName", "=", "?");
		s.addWhere("and", "u", "userPwd", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(userName);
		params.add(userPwd);

		return new SqlUtil().getMap(s.select(), params);
	}

	public static List<Map<String, Object>> getList() {

		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User", "u");
		s.addTable("Sys_Location", "l");

		s.addField("l", "cnName");

		s.addField("u", "userId");
		s.addField("u", "userName");
		s.addField("u", "userPwd");
		s.addField("u", "md5Pwd");
		s.addField("u", "userType");
		s.addField("u", "lastLogin");
		s.addField("u", "locationId");
		s.addField("u", "isDeleted");
		s.addField("u", "isLocked");
		s.addField("u", "insertTime");
		s.addField("u", "updateTime");

		s.addWhere("", "l", "locationId", "=", "u", "locationId");

		s.addOrderBy("u", "userId", true);

		return new SqlUtil().getList(s.select(), null);
	}

	public static PageRecords getPage(Integer pageSize, Integer currentPage,
			String locationIds, String msg) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User", "u");
		s.addTable("Sys_Location", "l");

		s.setTagField("u", "userId");

		s.addField("l", "cnName");

		s.addField("u", "userId");
		s.addField("u", "userName");
		s.addField("u", "userPwd");
		s.addField("u", "md5Pwd");
		s.addField("u", "userType");
		s.addField("u", "lastLogin");
		s.addField("u", "locationId");
		s.addField("u", "isDeleted");
		s.addField("u", "isLocked");
		s.addField("u", "insertTime");
		s.addField("u", "updateTime");

		s.addOrderBy("u", "userId", true);

		s.addWhere("", "l", "locationId", "=", "u", "locationId");

		if (!locationIds.isEmpty()) {
			s.addWhere("and", "u", "locationId", "in", "(" + locationIds + ")");
		}

		List<Object> params = new ArrayList<Object>();

		if (!msg.isEmpty()) {
			s.addWhere("and", "u", "userName", "like", "'%'||?||'%'");
			params.add(msg);
		}

		PageRecords pr = new PageRecords();
		pr.setCurrentPage(currentPage);
		pr.setPageSize(pageSize);

		SqlUtil db = new SqlUtil();

		pr.setRecordsCount((Integer) db.getValue(s.count(), params));
		pr.setBaseParam();

		params.add(pr.getPageSize());
		params.add(pr.getStartIndex());

		pr.setPageResult(db.getList(s.page(), params));

		return pr;
	}

	public static Boolean delete(Integer userId) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addWhere("and", "userId", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		return new SqlUtil().update(s.delete(), params) > 0;
	}

	public static Boolean remove(Integer userId) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("isDeleted");

		s.addWhere("and", "userId", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(userId);

		return new SqlUtil().update(s.update(), params) > 0;
	}

	public static Boolean setLock(Integer userId, Boolean lock) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("isLocked");

		s.addWhere("and", "userId", "=", "?");

		List<Object> params = new ArrayList<Object>();
		if (lock)
			params.add(true);
		else
			params.add(false);

		params.add(userId);

		return new SqlUtil().update(s.update(), params) > 0;
	}

	public static Long insert(Map<String, Object> content) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.setSequence("Sys_User", "userId");

		s.addField("userName");
		s.addField("userPwd");
		s.addField("md5Pwd");
		s.addField("userType");
		s.addField("lastLogin");
		s.addField("locationId");
		s.addField("isDeleted");
		s.addField("isLocked");
		s.addField("insertTime");
		s.addField("updateTime");

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		List<Object> params = new ArrayList<Object>();
		params.add(content.get("userName"));
		params.add(content.get("userPwd"));
		params.add(content.get("md5Pwd"));
		params.add(content.get("userType"));
		params.add(ts);
		params.add(content.get("locationId"));
		params.add(false);
		params.add(false);
		params.add(ts);
		params.add(ts);

		return new SqlUtil().insert(s.insert(), params, s.sequence());
	}

	public static Boolean update(Map<String, Object> content) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("userName");
		s.addField("userType");
		s.addField("locationId");
		s.addField("updateTime");

		s.addWhere("", "userId", "=", "?");

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		List<Object> params = new ArrayList<Object>();
		params.add(content.get("userName"));
		params.add(content.get("userType"));
		params.add(content.get("locationId"));
		params.add(ts);
		params.add(content.get("userId"));

		return new SqlUtil().update(s.update(), params) > 0;
	}

	public static Boolean setLastLogin(Integer userId) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("lastLogin");

		s.addWhere("", "userId", "=", "?");

		Timestamp ts = new Timestamp(System.currentTimeMillis());

		List<Object> params = new ArrayList<Object>();
		params.add(ts);
		params.add(userId);

		return new SqlUtil().update(s.update(), params) > 0;
	}

	public static Boolean updatePwd(String userPwd, String md5Pwd,
			Integer userId) {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("userPwd");
		s.addField("md5Pwd");

		s.addWhere("", "userId", "=", "?");

		List<Object> params = new ArrayList<Object>();
		params.add(userPwd);
		params.add(md5Pwd);
		params.add(userId);

		return new SqlUtil().update(s.update(), params) > 0;
	}

	public static Boolean updatePwd() {
		SqlBuilder s = new SqlBuilder();

		s.addTable("Sys_User");

		s.addField("userId");
		s.addField("md5Pwd");

		List<Object> params = new ArrayList<Object>();

		SqlUtil db = new SqlUtil();

		List<Map<String, Object>> list = db.getList(s.select(), params);

		if (list != null && list.size() > 0) {
			s.clearBuilder();
			s.addTable("Sys_User");
			s.addField("userPwd");
			s.addWhere("", "userId", "=", "?");

			List<List<Object>> paramsList = new ArrayList<List<Object>>();

			for (int i = 0, j = list.size(); i < j; j++) {
				params = new ArrayList<Object>();

				params.add(Tools.getPassword(list.get(i).get("md5Pwd")
						.toString().trim()));
				params.add((Integer) list.get(i).get("userId"));

				paramsList.add(params);
			}

			db.batch(s.update(), paramsList);
		}

		return true;
	}
}
