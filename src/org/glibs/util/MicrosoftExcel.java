package org.glibs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MicrosoftExcel {

	public static Boolean isExcel2003(String filePath) {
		try {
			new HSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static Boolean isExcel2007(String filePath) {
		try {
			new XSSFWorkbook(new FileInputStream(new File(filePath)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static List<Map<String, Object>> importExcel(String filePath)
			throws Exception {

		if (new File(filePath).exists()) {

			InputStream in = new FileInputStream(new File(filePath));
			Workbook workbook = null;

			if (isExcel2003(filePath)) {
				workbook = new HSSFWorkbook(in);
			} else if (isExcel2007(filePath)) {
				workbook = new XSSFWorkbook(in);
			} else {
				in.close();
				return null;
			}

			FormulaEvaluator fe = workbook.getCreationHelper()
					.createFormulaEvaluator();

			List<Map<String, Object>> list = null;

			int sheetCount = workbook.getNumberOfSheets();
			int rowCount = 0;
			int colCount = 0;

			int i, j;

			if (sheetCount > 0) {
				list = new ArrayList<Map<String, Object>>();

				Map<String, Object> item = null;
				Sheet sheet = null;
				Row firstRow = null;
				Row row = null;

				sheet = workbook.getSheetAt(0);
				rowCount = sheet.getLastRowNum();

				if (rowCount > 0) {
					firstRow = sheet.getRow(1);
					colCount = firstRow.getLastCellNum();

					for (i = 2; i <= rowCount; i++) {
						row = sheet.getRow(i);

						if (row == null) {
							continue;
						}

						item = new HashMap<String, Object>();

						for (j = 1; j < colCount; j++) {
							if (row.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
								item.put(firstRow.getCell(j)
										.getStringCellValue(),
										fe.evaluate(row.getCell(j))
												.getStringValue());
							} else {
								item.put(firstRow.getCell(j)
										.getStringCellValue(), row.getCell(j)
										.getStringCellValue());
							}
						}

						list.add(item);
					}
				}
			}

			return list;
		} else {
			return null;
		}
	}

	public static Boolean exportExcel(List<String> fields,
			List<Map<String, Object>> list, String filePath, Boolean isExcel2007)
			throws Exception {

		Workbook workbook = null;
		if (isExcel2007) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}

		if (fields == null || list == null || fields.size() == 0
				|| list.size() == 0) {
			return false;
		} else {

			Sheet sheet = workbook.createSheet("Sheet1");
			Row row = sheet.createRow(0);

			for (int h = 0; h < fields.size(); h++) {
				row.createCell(h).setCellValue(fields.get(h));
			}

			for (int i = 1, j = list.size(); i < j; i++) {
				row = sheet.createRow(i);
				for (int h = 0; h < fields.size(); h++) {
					row.createCell(h).setCellValue(
							list.get(i).get(fields.get(h)).toString());
				}
			}

			OutputStream fileOut = new FileOutputStream(filePath);
			workbook.write(fileOut);
			fileOut.close();

			if (new File(filePath).exists()) {
				return true;
			} else {
				return false;
			}
		}
	}
}
