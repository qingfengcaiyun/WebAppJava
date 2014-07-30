package org.glibs.text;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WebResultDo {

	public static String getMsg(String msg) {
		StringBuffer str = new StringBuffer();
		str.append("{\"msg\":\"");
		str.append(msg);
		str.append("\"}");
		return str.toString();
	}

	public static String getDataRow(Map<String, Object> dataRow) {
		if (dataRow == null || dataRow.isEmpty()) {
			return "{}";
		} else {
			StringBuffer str = new StringBuffer();
			Iterator<Entry<String, Object>> iter = dataRow.entrySet()
					.iterator();
			Entry<String, Object> entry = null;
			while (iter.hasNext()) {
				entry = (Entry<String, Object>) iter.next();
				str.append(",\"");
				str.append(entry.getKey().toString());
				str.append("\":");
				str.append("\"");
				str.append(entry.getValue().toString().replaceAll("\n", "\\n")
						.replaceAll("\r", "\\r"));
				str.append("\"");
			}

			return "{" + str.toString().substring(1) + "}";
		}
	}

	public static String getDataList(List<Map<String, Object>> dataList) {
		if (dataList != null && dataList.size() > 0) {
			StringBuffer str = new StringBuffer();
			for (int i = 0, j = dataList.size(); i < j; i++) {
				if (dataList.get(i).size() > 0) {
					str.append(",");
					str.append(getDataRow(dataList.get(i)));
				} else {
					continue;
				}
			}
			return "[" + str.toString().substring(1) + "]";
		} else {
			return "[]";
		}
	}
}
