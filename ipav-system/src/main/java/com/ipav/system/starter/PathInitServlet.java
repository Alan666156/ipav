package com.ipav.system.starter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipav.system.util.ContentUtil;
/**
 * 初始化属性文件的配置
 * @author: fuhongxing
 * @date:   2015年10月14日
 * @version 1.0.0
 */
public class PathInitServlet extends HttpServlet {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.service(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.service(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		File file=new File(uri);
		super.service(req, resp);
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		Properties prop = new Properties();  
		//InputStream is=getServletContext().getResourceAsStream("/WEB-INF/classes/metainfo.properties");
		InputStream is=this.getClass().getClassLoader().getResourceAsStream("metainfo.properties");
		try {
			prop.load(is);
			createImagePath(prop);
//			ContentUtil.FILEPATH=prop.getProperty("file.filePath");
			ContentUtil.EMAIL_PATH=prop.getProperty("system.emailpath");
			ContentUtil.EMAILHOST =prop.getProperty("system.emailhost");
			ContentUtil.EMAILFROM=prop.getProperty("system.emailfrom");
			ContentUtil.EMAILUSERNAME=prop.getProperty("system.emailusername");
			ContentUtil.EMAILPASSWORD =prop.getProperty("system.emailpassword");
			ContentUtil.LOGIN_PATH = prop.getProperty("system.loginpath");
			ContentUtil.SEND_MESSAGE_URL = prop.getProperty("system.sendmessageurl");
			
		} catch (IOException e) {
			logger.error("配置文件初始化异常"+e.getMessage());
		} finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				
		}
//		File file=new File(getServletContext().getRealPath("/")+"images");
//		if(!file.exists())
//			file.mkdir();
		super.init();
	}
	
/**
 * 生成子级映射目录路径
 * @author GaoYang
 * @param prop
 */
	private void createImagePath(Properties prop){
		ContentUtil.IMAGE_ROOT=prop.getProperty("images.root");
		ContentUtil.IMAGEPATHS.put("company", prop.getProperty("images.company"));
		ContentUtil.IMAGEPATHS.put("system", prop.getProperty("images.system"));
		ContentUtil.IMAGEPATHS.put("user", prop.getProperty("images.user"));
		ContentUtil.IMAGEPATHS.put("say", prop.getProperty("images.say"));
		ContentUtil.IMAGEPATHS.put("theme", prop.getProperty("images.theme"));
		ContentUtil.IMAGEPATHS.put("vote", prop.getProperty("images.vote"));
		ContentUtil.IMAGEPATHS.put("files", prop.getProperty("images.files"));
	}
}
