package org.glibs.text;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlDo {

	public static String msgToJson(String msg) {
		StringBuffer str = new StringBuffer();
		str.append("{\"msg\":\"");
		str.append(replaceCharForJson(msg));
		str.append("\"}");
		return str.toString();
	}

	public static String mapToJson(Map<String, Object> dataRow) {
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
				str.append(replaceCharForJson(entry.getKey().toString()));
				str.append("\":");
				str.append("\"");
				str.append(replaceCharForJson(entry.getValue().toString()));
				str.append("\"");
			}

			return "{" + str.toString().substring(1) + "}";
		}
	}

	public static String listToJson(List<Map<String, Object>> dataList) {
		if (dataList != null && dataList.size() > 0) {
			StringBuffer str = new StringBuffer();
			for (int i = 0, j = dataList.size(); i < j; i++) {
				if (dataList.get(i).size() > 0) {
					str.append(",");
					str.append(mapToJson(dataList.get(i)));
				} else {
					continue;
				}
			}
			return "[" + str.toString().substring(1) + "]";
		} else {
			return "[]";
		}
	}

	public static String replaceCharForJson(String s) {
		s = s.replaceAll("\b", "\\b");
		s = s.replaceAll("\t", "\\t");
		s = s.replaceAll("\n", "\\n");
		s = s.replaceAll("\f", "\\f");
		s = s.replaceAll("\r", "\\r");
		return s;
	}

	public static String clearHTMLTags(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}
}
