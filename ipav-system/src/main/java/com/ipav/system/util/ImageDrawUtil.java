package com.ipav.system.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipav.system.util.ftp.FTPUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月17日 上午9:54:46	
 * 上海天道启科电子有限公司
 */
public class ImageDrawUtil{
	/** * 验证码图片的宽度。 */
	private static int width = 103;  
	/** * 验证码图片的高度。 */
	private static int height = 44;   
	/** * 验证码字符个数 */
	private static int codeCount = 4;  
	/** * xx */
	private static int xx = 0;  
	/** * 字体高度 */
	private static int fontHeight;  
	/** * codeY */
	private static int codeY;  
	/** * codeSequence */
	private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', 
			'1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
	private static Logger logger = LoggerFactory.getLogger(ImageDrawUtil.class);

	public static void drawPicture(HttpServletRequest req,HttpServletResponse resp){
		xx = width / (codeCount + 2); 
		fontHeight = height - 4;  
		codeY = height - 4; 
		// 定义图像buffer 
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics2D gd = buffImg.createGraphics();  
		// 创建一个随机数生成器类 
		Random random = new Random();  
		// 将图像填充为白色
		gd.setColor(Color.WHITE); 
		gd.fillRect(0, 0, width, height);  
		// 创建字体，字体的大小应该根据图片的高度来定。 
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight); 
		// 设置字体。
		gd.setFont(font);  
		// 画边框。 
		gd.setColor(Color.BLACK); 
		gd.drawRect(0, 0, width - 1, height - 1);  
		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。 
		gd.setColor(Color.BLACK); 
		for (int i = 0; i < 160; i++) { 
			int x = random.nextInt(width); 
			int y = random.nextInt(height); 
//			int xl = random.nextInt(12); 
//			int yl = random.nextInt(12); 
			gd.drawOval(x, y, 1,1 ); 
		}  
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer(); 
		int red = 0, green = 0, blue = 0;  
		// 随机产生codeCount数字的验证码。 
		for (int i = 0; i < codeCount; i++) { 
			// 得到随机产生的验证码数字。 
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]); 
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255); 
			green = random.nextInt(255); 
			blue = random.nextInt(255);  
			// 用随机产生的颜色将验证码绘制到图像中。 
			gd.setColor(new Color(red, green, blue)); 
			gd.drawString(strRand, (i + 1) * xx, codeY);  
			// 将产生的四个随机数组合在一起。 
			randomCode.append(strRand); 
			} 
		// 将四位数字的验证码保存到Session中。 
		HttpSession session = req.getSession(); 
		session.setAttribute("validateCode",randomCode.toString()); 
		System.out.println("=============生成校验码=========="+randomCode.toString()+"=========="); 
		// 禁止图像缓存。 
		resp.setHeader("Pragma", "no-cache"); 
		resp.setHeader("Cache-Control", "no-cache"); 
		resp.setDateHeader("Expires", 0); 
		resp.setContentType("image/jpeg"); 
		 
		// 将图像输出到Servlet输出流中。 
		try {
			ServletOutputStream sos = resp.getOutputStream(); 
			ImageIO.write(buffImg, "jpeg", sos); 
			sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 剪切图片
	 * @param req
	 * @param resp
	 */
	public static void cutPicture(BufferedImage image,Map map,String dirpath)throws Exception{
        String x= map.get("x").toString();
        String y= map.get("y").toString();
        String xwidth= map.get("width").toString();
        String yheight= map.get("height").toString();
        String pname= map.get("pname").toString();
        BufferedImage bi = image.getSubimage((new BigDecimal(x)).intValue(), (new BigDecimal(y)).intValue(), 
        		(new BigDecimal(xwidth)).intValue(), (new BigDecimal(yheight)).intValue());
        FTPUtil ftp = new FTPUtil(); 
        ftp.upload(getImageStream(bi), dirpath, pname);
	}
	/**
	 * BufferedImage变成inputstream
	 * @author GaoYang
	 * @param bi
	 * @return
	 */
	public static InputStream getImageStream(BufferedImage bi){ 
        InputStream is = null; 
         
        bi.flush(); 
         
        ByteArrayOutputStream bs = new ByteArrayOutputStream();  
         
        ImageOutputStream imOut; 
        try { 
            imOut = ImageIO.createImageOutputStream(bs); 
             
            ImageIO.write(bi, "png",imOut); 
             
            is= new ByteArrayInputStream(bs.toByteArray()); 
             
        } catch (IOException e) { 
            e.printStackTrace(); 
        }  
        return is; 
    }
	/**
	 * 压缩图片实现
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 * @throws IOException 
	 */
	 public static BufferedImage resize(InputStream in, int  targetW,  int targetH){
		 BufferedImage target = null;     
         try {  
            // 读取图片文件  
            BufferedImage source=ImageIO.read(in);
            	if (targetW > 0 || targetH > 0){
				 	int type = source.getType();     
			        double sx = (double) targetW / source.getWidth();     
			        double sy = (double) targetH / source.getHeight();   
			        if (type == BufferedImage.TYPE_CUSTOM) { 
			            ColorModel cm = source.getColorModel();     
			            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);     
			            boolean alphaPremultiplied = cm.isAlphaPremultiplied();     
			            target = new BufferedImage(cm, raster, alphaPremultiplied, null);     
			        } else {   
			            target = new BufferedImage(targetW, targetH, type);     
			        }
			        Graphics2D g = target.createGraphics();     
			        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);     
			        g.drawRenderedImage(source,  AffineTransform.getScaleInstance(sx, sy));     
			        g.dispose();  
            	}
            }  catch (Exception e) { 
            	logger.error("图片压缩异常 "+e.getMessage());
            }  finally {  
                if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						logger.error("InputStream IO流关闭异常  "+e.getMessage());
					}  
            }  
	        return target;     
	    }  
	
}
