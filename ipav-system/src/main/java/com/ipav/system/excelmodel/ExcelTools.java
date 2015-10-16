package com.ipav.system.excelmodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ipav.system.util.ContentUtil;

public class ExcelTools {

	/************************************ EXCEL导出 **********************************************/

	/**
	 * 列项添加值
	 * 
	 * @param type
	 * @param value
	 * @param cell
	 */
	public static void setCellValue(int type, Object value, Cell cell) {
		if (type == ContentUtil.EXCEL_TYPE_INT)
			cell.setCellValue(getIntValue(value));
		else if (type == ContentUtil.EXCEL_TYPE_FLOAT)
			cell.setCellValue(getFloatValue(value));
		else
			cell.setCellValue(getStringValue(value));
	}

	public static int getIntValue(Object value) {
		return Integer.parseInt(value.toString());
	}

	public static float getFloatValue(Object value) {
		return Float.parseFloat(value.toString());
	}

	public static String getStringValue(Object value) {
		return value.toString();
	}

	/**
	 * 获取列值类型
	 * 
	 * @param obj
	 * @return
	 */
	public static int getCellValueType(Object obj) {
		String type = obj.getClass().getSimpleName();
		int result = ContentUtil.EXCEL_TYPE_TEXT;
		if ("Integer".equals(type))
			result = ContentUtil.EXCEL_TYPE_INT;
		else if ("Float".equals(type))
			result = ContentUtil.EXCEL_TYPE_FLOAT;
		return result;
	}

	/**
	 * 设置列项类型
	 * 
	 * @param cell
	 * @param type
	 */
	public static void setCellType(Cell cell, int type) {
		if (type == ContentUtil.EXCEL_TYPE_TEXT)
			cell.setCellType(Cell.CELL_TYPE_STRING);
		else if (type == ContentUtil.EXCEL_TYPE_INT
				|| type == ContentUtil.EXCEL_TYPE_FLOAT)
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	}

	/**
	 * 
	 * @param os
	 * @param flag
	 *            true-xslx,false-.xls
	 * @param sheetName
	 *            sheet名
	 * @param title
	 *            表格项名及格式
	 * @param content
	 *            表格内容
	 * @return
	 */
	public boolean createExcel(OutputStream os, boolean flag, String sheetName,
			List<String> title, List content) {
		boolean isSuccess = false;
		if (flag) {
			isSuccess = this.createExcel07(os, sheetName, title, content);
		} else
			isSuccess = this.createExcel03(os, sheetName, title, content);
		return isSuccess;
	}

	/**
	 * 导出xls文件
	 * 
	 * @param os
	 * @param sheetName
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean createExcel03(OutputStream os, String sheetName,
			List<String> title, List<List> content) {
		boolean isSuccess = false;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个工作空间
		HSSFRow row = null;// 创建一行
		HSSFCell cell = null;// 每个单元格
		int line = 0;// 内容起始行
		int rowLength = 0;// 行长度
		int cellLength = 0;// 列长度
		List rowContent = null;
		Object cellValue = null;
		int cellValueType = 0;
		// 表格头
		if (title != null) {
			cellLength = title.size();
			row = sheet.createRow(0);
			for (int i = 0; i < cellLength; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(title.get(i));
			}
			line = 1;
		}
		// 表格内容
		if (content != null&&content.size()>0) {
			rowLength = content.size();
			for (int i = 0; i < rowLength; i++) {
				row = sheet.createRow(line + i);
				rowContent = content.get(i);
				cellLength = rowContent.size();
				for (int j = 0; j < cellLength; j++) {
					cell = row.createCell(j);
					cellValue = rowContent.get(j);
					cellValueType = this.getCellValueType(cellValue.getClass()
							.getSimpleName());
					this.setCellType(cell, cellValueType);
					this.setCellValue(cellValueType, cellValue, cell);
				}
			}
		}
		// excel输出到流中
		try {
			workbook.write(os);
			isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	/**
	 * 导出xlsx文件
	 * 
	 * @param os
	 * @param sheetName
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean createExcel07(OutputStream os, String sheetName,
			List<String> title, List<List> content) {
		boolean isSuccess = false;
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个工作空间
		XSSFRow row = null;// 创建一行
		XSSFCell cell = null;// 每个单元格
		int line = 0;// 内容起始行
		int rowLength = 0;// 行长度
		int cellLength = 0;// 列长度
		List rowContent = null;
		Object cellValue = null;
		int cellValueType = 0;
		// 表格头
		if (title != null) {
			cellLength = title.size();
			row = sheet.createRow(0);
			for (int i = 0; i < cellLength; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(title.get(i));
			}
			line = 1;
		}
		// 表格内容
		if (content != null&&content.size()>0) {
			rowLength = content.size();
			for (int i = 0; i < rowLength; i++) {
				row = sheet.createRow(line + i);
				rowContent = content.get(i);
				cellLength = rowContent.size();
				for (int j = 0; j < cellLength; j++) {
					cell = row.createCell(j);
					cellValue = rowContent.get(j);
					cellValueType = this.getCellValueType(cellValue.getClass()
							.getSimpleName());
					this.setCellType(cell, cellValueType);
					this.setCellValue(cellValueType, cellValue, cell);
				}
			}
		}
		// excel输出到流中
		try {
			workbook.write(os);
			isSuccess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	/************************************ EXCEL读取 **********************************************/

	/**
	 * 遍历Excel内容
	 * 
	 * @param result
	 * @param rows
	 */
	public void getExcelContent(List result, Iterator<Row> rows) {
		List<String> cellValues = null;
		Iterator<Cell> cells = null;
		Cell cell = null;
		while (rows.hasNext()) {
			cellValues = new ArrayList<String>();
			cells = rows.next().cellIterator();
			while (cells.hasNext()) {
				cell = cells.next();
				cellValues.add(this.getCellValue(cell));
			}
			result.add(cellValues);
		}
	}

	public static String getCellValue(Cell cell) {
		String result = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = String.valueOf(cell.getNumericCellValue());
			break;
		}
		return result;
	}

	/**
	 * 读取xls文件
	 * 
	 * @param is
	 * @return
	 */
	public List<ArrayList<String>> readExcel03(InputStream is) {
		List result = new ArrayList();
		POIFSFileSystem poiFile = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		Iterator<Row> rows = null;
		Iterator<Cell> cells = null;
		Row row = null;
		Cell cell = null;
		try {
			poiFile = new POIFSFileSystem(is);
			workbook = new HSSFWorkbook(poiFile);
			sheet = workbook.getSheetAt(0);
			rows = sheet.rowIterator();
			this.getExcelContent(result, rows);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 读取xlsx文件
	 * 
	 * @param is
	 * @return
	 */
	public List<ArrayList<String>> readExcel07(InputStream is) {
		List result = new ArrayList();
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		Iterator<Row> rows = null;
		Iterator<Cell> cells = null;
		Row row = null;
		Cell cell = null;
		try {
			workbook = new XSSFWorkbook(is);
			sheet = workbook.getSheetAt(0);
			rows = sheet.rowIterator();
			this.getExcelContent(result, rows);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
