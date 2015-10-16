package com.ipav.system.util.ftp;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.PreparedStatement;

public class FTPUtil {

	private String userName;

	private String pwd;

	private String host;

	private int port;

	private static FTPClient client;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	public FTPUtil(String userName, String pwd, String host, int port) {
		super();
		this.userName = userName;
		this.pwd = pwd;
		this.host = host;
		this.port = port;
	}

	public FTPUtil() {
		super();
		this.userName = "testFTP";
		this.pwd = "test";
		this.host = "172.16.2.160";
		this.port = 21;
		PreparedStatement ps = null;
	}

	/************************ ftpclient ***********************************/
	public boolean ftpConnect() {
		if (client == null || !client.isConnected()) {
			if (client == null){
				client = new FTPClient();
			}
			client.setControlEncoding("UTF-8");
			client.setDefaultPort(port);
			try {
				client.connect(host);
				boolean flag = client.login(userName, pwd);
				logger.info("FTP login "+(flag==true?"success":"failed"));
				// client.addProtocolCommandListener(new
				// PrintCommandListener(new PrintWriter(System.out)));
				if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
					client.disconnect();
					return false;
				}
			} catch (Exception e) {
				logger.error("ftp connect Excepiton"+e.getMessage());
				if (client.isConnected()) {
					try {
						client.disconnect();//断开ftp
					} catch (IOException ioe) {
						logger.error("FTP disconnect Exception"+ioe.getMessage());
					}
				}
			}
		}
		return true;
	}

	/**
	 * 文件删除
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean delete(String fileName, String dirpath) {
		boolean success = false;
		ftpConnect();
		try {
			if (dirpath != null){
				client.changeWorkingDirectory(dirpath);
			}
			for (int i = 0; i < client.listFiles(fileName).length; i++){
				logger.info(client.listFiles(fileName)[i].getName());
			}
			success = client.deleteFile(fileName);
			logger.info("文件删除"+(success==true?"success":"failed"));
			client.logout();
		} catch (IOException e) {
			logger.error(fileName+"文件删除异常"+e.getMessage());
		} finally {
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException ioe) {
					logger.error("FTP disconnect Exception"+ioe.getMessage());
				}
			}
		}
		return success;
	}

	/**
	 * 上传
	 * 
	 * @param is
	 *            需上传的文件流
	 * @param dirpath
	 *            目标路径
	 * @param dirFileName
	 *            目标文件名
	 * @return
	 */
	public boolean upload(InputStream is, String dirpath, String dirFileName) {
		boolean success = false;
		if (!ftpConnect()) {
			success = true;
			return success;
		}
		try {
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			if (dirpath != null){
				client.changeWorkingDirectory(dirpath);
				logger.info("ftp work path -->"+client.printWorkingDirectory());
			}
			boolean flag = client.storeFile(new String(dirFileName.getBytes("UTF-8"),"iso-8859-1"), is);
			
			logger.info(dirFileName+" 上传 "+(flag==true?"success":"failed"));
			is.close();
			client.logout();
		} catch (IOException e) {
			logger.error("文件上传异常"+e.getMessage());
		} finally {
			if (client.isConnected()) {
				try {
					client.disconnect();
					logger.info("FTP disconnect Success");
				} catch (IOException ioe) {
					logger.error("FTP disconnect Exception"+ioe.getMessage());
				}
			}
		}
		success = true;
		return success;
	}

	public InputStream resizeImg(int width, int height, InputStream is,String type)
			throws IOException {
		Image img = ImageIO.read(is);
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		bi.getGraphics().drawImage(
				img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,
				null);
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		ImageIO.write(bi, type, os);
		return new ByteArrayInputStream(os.toByteArray()); 
	}

	public static void main(String[] args) throws IOException {
		FTPUtil util = new FTPUtil();
		// System.out.println(util.delete("1425893089038.png"));
		//util.resizeImg(100, 100, new FileInputStream(new File("C:\\Users\\user\\Desktop\\pic.png")),"png");
		util.upload(util.resizeImg(100, 100, new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\jupiter\\jupiter-web\\src\\main\\resources\\static\\images\\logo.png")),"png"), "company", "logo.png");
		//util.upload(new FileInputStream(new File("C:\\Users\\user\\Desktop\\pic.png")), null, "test.png");
	}
}
