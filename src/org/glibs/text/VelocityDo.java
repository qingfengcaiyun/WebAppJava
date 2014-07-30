package org.glibs.text;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public class VelocityDo {

	public static String buildStringByTemplate(Map<String, Object> content,
			String srcFile, String dirPath) {

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, dirPath);
		ve.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
		ve.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
		ve.init();

		VelocityContext vc = new VelocityContext();

		if (content != null && !content.isEmpty()) {
			Iterator<Entry<String, Object>> iter = content.entrySet()
					.iterator();
			Entry<String, Object> entry = null;

			while (iter.hasNext()) {
				entry = (Entry<String, Object>) iter.next();
				vc.put(entry.getKey().toString(), entry.getValue());
			}
		}

		StringWriter vw = new StringWriter();
		Template vt = ve.getTemplate(srcFile);
		vt.merge(vc, vw);

		return vw.getBuffer().toString();
	}
}
