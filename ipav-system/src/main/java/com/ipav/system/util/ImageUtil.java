package com.ipav.system.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.ipav.system.util.ftp.FTPUtil;

public class ImageUtil {

	public File cut(int x,int y,int width,int height,File file){
		FileInputStream is=null;
		ImageInputStream iis=null;
		String fileType=file.getName().substring(file.getName().lastIndexOf(".")+1);
		File dest=null;
		try{   
			dest=new File(file.getParent()+File.separator+Calendar.getInstance().getTimeInMillis()+"."+fileType);
            //读取图片文件
			is = new FileInputStream(file); 
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 
             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等 。 
             */
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileType);  
            ImageReader reader = it.next(); 
            //获取图片流 
            iis = ImageIO.createImageInputStream(is);
               
            /* 
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis,true) ;
            
            /* 
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回 
             * ImageReadParam 的实例。  
             */
            ImageReadParam param = reader.getDefaultReadParam(); 
             
            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
             */ 
            Rectangle rect = new Rectangle(x, y, width, height); 
            
              
            //提供一个 BufferedImage，将其用作解码像素数据的目标。 
            param.setSourceRegion(rect); 

            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0,param);                
      
            //保存新图片 
            ImageIO.write(bi, fileType, dest); 
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
            if(is!=null)
				try {
					is.close() ;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}       
            if(iis!=null)
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}  
        }
		return dest;
	}
	
	public String cutFTPImage(int x,int y,int width,int height,String fileType,String urlpath,String dir,String oldName){
//		FileInputStream is=null;
		ImageInputStream iis=null;
//		File dest=null;
		String filename="";
		try{   
            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 
             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等 。 
             */
			URL url=new URL(urlpath+oldName);
			if(fileType.equals("")){
				HttpURLConnection urlconnection=(HttpURLConnection) url.openConnection();
			    urlconnection.connect();
			    fileType=urlconnection.getContentType();
			    fileType=fileType.substring(fileType.indexOf("/")+1);
			    urlconnection.disconnect();
			}
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileType);  
            ImageReader reader = it.next(); 
            //获取图片流 
            iis = ImageIO.createImageInputStream(url.openConnection().getInputStream());
            
            /* 
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             *  避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis,true) ;
            
            /* 
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回 
             * ImageReadParam 的实例。  
             */
            ImageReadParam param = reader.getDefaultReadParam(); 
             
            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。 
             */ 
            Rectangle rect = new Rectangle(x, y, width, height); 
            
              
            //提供一个 BufferedImage，将其用作解码像素数据的目标。 
            param.setSourceRegion(rect); 

            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi = reader.read(0,param);                
      
            //保存新图片 
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageIO.write(bi, fileType, bs); 
            FTPUtil util=new FTPUtil();
            filename=Calendar.getInstance().getTimeInMillis()+"."+fileType;
            util.upload(new ByteArrayInputStream(bs.toByteArray()), dir, filename);
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
            if(iis!=null)
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}  
        }
		return filename;
//		return dest;
	}
	
/**
 * 保存图片到ftp
 * @author GaoYang
 * @param image
 * @param filepath
 * @return
 * @throws IOException
 */
	public static String saveImage(MultipartFile image,String filepath) throws IOException{
		String imagePath="";
		if (image != null && !image.isEmpty()) {
			FTPUtil util=new FTPUtil();
			// 获取文件原名
			String oldfilename = image.getOriginalFilename();
			// 获取文件的扩展名
			String extensionName = oldfilename.substring(oldfilename.lastIndexOf(".")+1, oldfilename.length());
			// 新的文件路径名 = 获取时间戳+"."文件扩展名
			imagePath = System.currentTimeMillis()+ "." + extensionName;
			//删除,防止重复上传
			util.delete(imagePath,filepath);
			// 执行保存在服务器
			util.upload(image.getInputStream(),filepath, imagePath);
		}
		return imagePath;
	}
	public static void main(String[] args) throws IOException, URISyntaxException{
		FileInputStream is=null;
		ImageInputStream iis=null;
		URL url=new URL("http://192.168.3.76/images/1425029605460.png");
		Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("png");  
        ImageReader reader = it.next(); 
        iis = ImageIO.createImageInputStream(url.openConnection().getInputStream());
        reader.setInput(iis,true) ;
        ImageReadParam param = reader.getDefaultReadParam(); 
        Rectangle rect = new Rectangle(0, 0, 50, 50); 
        param.setSourceRegion(rect); 
        BufferedImage bi = reader.read(0,param);   
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", bs); 
        FTPUtil util=new FTPUtil();
        util.upload(new ByteArrayInputStream(bs.toByteArray()), "http://192.168.3.76/images/", "test.png");
	}
}
