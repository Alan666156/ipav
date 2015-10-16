package com.ipav.system.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

import com.deppon.foss.framework.shared.encypt.base64.BASE64Decoder;
import com.deppon.foss.framework.shared.encypt.base64.BASE64Encoder;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月18日 下午7:32:51	
 * 上海天道启科电子有限公司
 */
public class PwdUtil {
	
	public final static String MD5(String s) {
        char hexDigits[] = {
                           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                           'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return null;
    }
	private static String keys[] ={
		"A","B","C","D","E","F","G","H","I","J","K","L","N","M","O","P","Q","R","S","T",
		"U","V","W","X","Y","Z"};
	
	public static String createPwd(){
		Random rd = new Random();
		int str= rd.nextInt(999999)%(899999+1) + 100000;
		return str+"";
	}
	
	public static String MD5Pwd(){
		return MD5(createPwd());
	}
	
	/**********************************BASE64加密解密***********************************************/
    // 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
 
	
}
