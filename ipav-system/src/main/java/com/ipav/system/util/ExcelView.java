package com.ipav.system.util;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelView extends AbstractExcelView {
	
	private String sheetName;
	
	private String fileName;
	
	public ExcelView(String sheetName, String fileName) {
		this.sheetName = sheetName;
		this.fileName = fileName;
	}


	@Override
	protected void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook arg1, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		String filename = fileName+".xls";//设置下载时客户端Excel的名称     
        filename = encodeFilename(filename, request);//处理中文文件名  
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        if(map.containsKey("content"))
        	new ExcelTools().createExcel(response.getOutputStream(), false, sheetName, (List<String>)map.get("title"),  (List<List>)map.get("content"));
        else 
        	new ExcelTools().createExcel(response.getOutputStream(), false, sheetName, (List<String>)map.get("title"), null);
	}


	/**  
	* 设置下载文件中文件的名称  
	*   
	* @param filename  
	* @param request  
	* @return  
	*/    
	public static String encodeFilename(String filename, HttpServletRequest request) {    
		/**  
		* 获取客户端浏览器和操作系统信息  
		* 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)  
		* 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6  
		*/    
		String agent = request.getHeader("USER-AGENT");    
		try {    
			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {    
				String newFileName = URLEncoder.encode(filename, "UTF-8");    
				newFileName = StringUtils.replace(newFileName, "+", "%20");    
				if (newFileName.length() > 150) {    
					newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");    
					newFileName = StringUtils.replace(newFileName, " ", "%20");    
				}    
				return newFileName;    
			}    
			if ((agent != null) && (-1 != agent.indexOf("Mozilla")))    
				return MimeUtility.encodeText(filename, "UTF-8", "B");    
			return filename;    
		} catch (Exception ex) {    
			return filename;    
		}    
	}   
}
