package org.webapp.service.sys;

import java.util.List;
import java.util.Map;

import org.webapp.dao.sys.LocationDao;
import org.webapp.dao.sys.LocationType;

public class LocationService {
	public static Map<String, Object> getOne(Integer locationId) {
		return LocationDao.getOne(locationId);
	}

	public static List<Map<String, Object>> getList() {
		return LocationDao.getList();
	}

	public static List<Map<String, Object>> getList(String parentNo) {
		return LocationDao.getList(parentNo);
	}

	public static List<Map<String, Object>> getList(LocationType lType) {
		return LocationDao.getList(lType);
	}

	public static Long insert(Map<String, Object> content) {
		return LocationDao.insert(content);
	}

	public static Boolean update(Map<String, Object> content) {
		return LocationDao.update(content);
	}
}
