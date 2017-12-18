package com.yundao.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yundao.core.utils.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Maps;
import com.yundao.core.utils.CloseableUtils;

/**
 * excel工具类
 *
 * @author wupengfei wupf86@126.com
 *
 */
public abstract class ExcelUtils {

	/**
	 * 读取excel文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<Map<Integer, String>> readExcel(File file) throws Exception {
		if (file == null) {
			return null;
		}
		return readExcel(file.getName(), new FileInputStream(file));
	}

	/**
	 * 读取excel文件
	 * 
	 * @param fileName
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static List<Map<Integer, String>> readExcel(String fileName, InputStream is) throws Exception {
		if (BooleanUtils.isBlank(fileName) || is == null) {
			return null;
		}
		int index = fileName.lastIndexOf(".");
		String extension = index == -1 ? "" : fileName.substring(index + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(is);
		}
		else if ("xlsx".equals(extension)) {
			return read2007Excel(is);
		}
		return null;
	}

	/**
	 * 读取excel2003版本
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static List<Map<Integer, String>> read2003Excel(InputStream is) throws IOException {
		List<Map<Integer, String>> result = new ArrayList<Map<Integer, String>>();
		HSSFWorkbook workBook = null;
		try {
			workBook = new HSSFWorkbook(is);
			HSSFSheet sheet = workBook.getSheetAt(0);
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				Map<Integer, String> rowValueMap = Maps.newHashMap();
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}

					// 根据类型获取值
					String value = null;
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_FORMULA:
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						value = String.valueOf(cell.getNumericCellValue());
						break;
					default:
						HSSFDataFormatter formatter = new HSSFDataFormatter();
						value = formatter.formatCellValue(cell);
					}
					rowValueMap.put(j, value);
				}
				result.add(rowValueMap);
			}
			return result;
		}
		finally {
			if (workBook != null) {
				workBook.close();
			}
			CloseableUtils.close(is);
		}
	}

	/**
	 * 读取excel2007版本
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static List<Map<Integer, String>> read2007Excel(InputStream is) throws Exception {
		List<Map<Integer, String>> result = new ArrayList<Map<Integer, String>>();
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(is);
			XSSFSheet sheet = workBook.getSheetAt(0);
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				Map<Integer, String> rowValueMap = Maps.newHashMap();
				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}

					// 根据类型获取值
					String value = null;
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_FORMULA:
						value = cell.getRawValue();
						break;
					default:
						HSSFDataFormatter formatter = new HSSFDataFormatter();
						value = formatter.formatCellValue(cell);
					}
					rowValueMap.put(j, value);
				}
				result.add(rowValueMap);
			}
			return result;
		}
		finally {
			if (workBook != null) {
				workBook.close();
			}
		}
	}

}