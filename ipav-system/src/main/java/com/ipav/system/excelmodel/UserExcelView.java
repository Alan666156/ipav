package com.ipav.system.excelmodel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月12日 下午2:37:19	
 * 上海天道启科电子有限公司
 */
public class UserExcelView extends BaseExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		this.setFlieName(response, request, "员工导入模板.xls");
		 if(model.containsKey("content"))
			 	 createExcel03(response.getOutputStream(), "员工导入模板", (List<String>)model.get("title"),  (List<Map>)model.get("content"));
	        else 
	        	new ExcelTools().createExcel(response.getOutputStream(), false, "员工导入模板", (List<String>)model.get("title"), null);
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
			List<String> title, List<Map> content) {
		boolean isSuccess = false;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);// 创建一个工作空间
		HSSFRow row = null;// 创建一行
		HSSFCell cell = null;// 每个单元格
		int line = 0;// 内容起始行
		int rowLength = 0;// 行长度
		int cellLength = 0;// 列长度
		Map rowContent = null;
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
				
				row.createCell(0).setCellValue(getStringValue(rowContent.get("userno")));
				row.createCell(1).setCellValue(getStringValue(rowContent.get("username")));
				row.createCell(2).setCellValue(getStringValue(rowContent.get("sex")));
				row.createCell(3).setCellValue(getStringValue(rowContent.get("orgname")));
				row.createCell(4).setCellValue(getStringValue(rowContent.get("mobile")));
				row.createCell(5).setCellValue(getStringValue(rowContent.get("email")));
				row.createCell(6).setCellValue(getStringValue(rowContent.get("valflg")));
				row.createCell(7).setCellValue(getStringValue(rowContent.get("sysid")));
				row.createCell(8).setCellValue(getStringValue(rowContent.get("phone")));
				row.createCell(9).setCellValue(getStringValue(rowContent.get("address")));
				row.createCell(10).setCellValue(getStringValue(rowContent.get("chefflg")));
			 
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
	
	private String getStringValue(Object obj){
		if(obj!=null){
			return obj.toString();
		}else{
			return ""; 
		}
	}

}
