package org.glibs.text;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

public class Tools {

	public static String getPassword(String str) {
		str = CryptDo.Encrypte(str, "SHA-512");

		StringBuffer s = new StringBuffer();
		for (int i = 0, j = str.length(); i < (j / 2); i++) {
			s.append(str.substring(i, j - i));
		}

		return CryptDo.Encrypte(s.toString(), "SHA-512");
	}

	public static String repeatString(String str, int n) {
		StringBuilder arr = new StringBuilder();
		for (int i = 0; i < n; i++) {
			arr.append(str);
		}
		return arr.toString();
	}

	public static String CheckForSave(String str) {
		str = str.replace('\'', '‘');
		String[] fKeys = { "drop table", "drop database", "insert into",
				"delete", "update", "select", "count", "execute", "exec",
				"join", "union", "where", "like", "create table",
				"create database", "alter", "truncate", "exists" };
		for (int i = 0; i < fKeys.length; i++) {
			str = str.replace(fKeys[i], CryptDo.MD5(fKeys[i]));
		}
		return str;
	}

	public static String CheckForRead(String str) {
		str = str.replace('\'', '‘');
		String[] fKeys = { "drop table", "drop database", "insert into",
				"delete", "update", "select", "count", "execute", "exec",
				"join", "union", "where", "like", "create table",
				"create database", "alter", "truncate", "exists" };
		for (int i = 0; i < fKeys.length; i++) {
			str = str.replace(CryptDo.MD5(fKeys[i]), fKeys[i]);
		}
		return str;
	}

	/**/
	// / <summary>
	// / 将数据库中取出的字符串
	// / </summary>
	// / <param name="str">string，指定字符串！</param>
	// / <returns>Boolean</returns>
	public static String HtmlStr(String str) {
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		return str;
	}

	public static void buildPath(String basePath) {

		Calendar c = Calendar.getInstance();

		String year = Integer.toString(c.get(Calendar.YEAR));
		String month = Integer.toString(c.get(Calendar.MONTH));
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		String hour = Integer.toString(c.get(Calendar.HOUR));

		String filePath = basePath + File.separator + year;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}

		filePath = filePath + File.separator + month;
		file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}

		filePath = filePath + File.separator + day;
		file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}

		filePath = filePath + File.separator + hour;
		file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static String HashMaptoJSON(Map<String, Object> map) {
		if (map.isEmpty()) {
			return "";
		} else {
			StringBuffer str = new StringBuffer();
			Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
			Entry<String, Object> entry = null;
			while (iter.hasNext()) {
				entry = (Entry<String, Object>) iter.next();
				str.append(",\"");
				str.append(entry.getKey().toString());
				str.append("\":");
				str.append("\"");
				str.append(entry.getValue().toString());
				str.append("\"");
			}

			return "{" + str.toString().substring(1) + "}";
		}
	}

	public static String ListMaptoJSON(List<Map<String, Object>> list) {
		if (list.size() > 0) {
			StringBuffer str = new StringBuffer();
			for (int i = 0, j = list.size(); i < j; i++) {
				if (list.get(i).size() > 0) {
					str.append(",");
					str.append(HashMaptoJSON(list.get(i)));
				} else {
					continue;
				}
			}
			return "[" + str.toString().substring(1) + "]";
		} else {
			return "[]";
		}
	}

	public static String readToString(String filePath) {
		StringBuffer str = new StringBuffer();

		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));

			String s = r.readLine();
			while (s != null) {
				str.append(s);
				str.append("\n");

				s = r.readLine();
			}

			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(str.toString());
		return str.toString();
	}

	// beanconvert

	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map, Object obj) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}

		return;
	}

	// Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
	public static void transMap2Bean2(Map<String, Object> map, Object obj) {
		if (map == null || obj == null) {
			return;
		}
		try {
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			System.out.println("transMap2Bean2 Error " + e);
		}
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;
	}

	// public static void main(String[] args) {
	//
	// PersonBean person = new PersonBean();
	// Map<String, Object> mp = new HashMap<String, Object>();
	// mp.put("name", "Mike");
	// mp.put("age", 25);
	// mp.put("mN", "male");
	//
	// // 将map转换为bean
	// transMap2Bean2(mp, person);
	//
	// System.out.println("--- transMap2Bean Map Info: ");
	// for (Map.Entry<String, Object> entry : mp.entrySet()) {
	// System.out.println(entry.getKey() + ": " + entry.getValue());
	// }
	//
	// System.out.println("--- Bean Info: ");
	// System.out.println("name: " + person.getName());
	// System.out.println("age: " + person.getAge());
	// System.out.println("mN: " + person.getmN());
	//
	// // 将javaBean 转换为map
	// Map<String, Object> map = transBean2Map(person);
	//
	// System.out.println("--- transBean2Map Map Info: ");
	// for (Map.Entry<String, Object> entry : map.entrySet()) {
	// System.out.println(entry.getKey() + ": " + entry.getValue());
	// }
	//
	// }

	// beanconvert
}
