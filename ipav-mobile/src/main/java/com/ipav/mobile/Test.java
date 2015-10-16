package com.ipav.mobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Test {
	
//	private final String url="http://localhost:8080/ipav-mobile/ipav/mobile/service";
	
	private final String url="http://192.168.3.78:8080/ipav-mobile/ipav/mobile/service";
	
//	private final String url="http://192.168.3.186:8080/ipav/mobile/service";

//	private final String url="http://192.168.3.77:8090/ipav-mobile/ipav/mobile/service";
	 
 
	
	@org.junit.Test
	public void test10002(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10002);
		body.put("registerUser", "13122896552");
		body.put("type", "M");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
			System.out.println("========================");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test10005(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10005);
		body.put("sendTo", "13122896552");
		body.put("optType", "1");
		body.put("type", "M");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
			System.out.println("========================");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@org.junit.Test
	public void test10012(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10012);
		body.put("phone", "13122896552");
		body.put("validCode", 197732);
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
			System.out.println("========================");
//			this.post(url, param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@org.junit.Test
	public void test10007(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10007);
		body.put("userId", "1000119admin");
//		body.put("minMonth", "201412");
//		body.put("maxMonth", "201501");
		body.put("id", 64);
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
			System.out.println("========================");
//			this.post(url, param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@org.junit.Test
	public void test10006(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10006);
		body.put("companyid", "1000126");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//GaoYang Notice Test
	@org.junit.Test
	public void test10017(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10017);
		body.put("userid", "1000136admin");
		param.put("header", header);
		param.put("body", body);
		try {
			System.out.println("========================");
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void test10018(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10018);
		body.put("userid", "1000136admin");
		body.put("noticeid", "26");
		param.put("header", header);
		param.put("body", body);
		try {
			System.out.println("========================");
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void test10019(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10019);
		body.put("noticeid", "26");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test10020(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10020);
		body.put("noticeid", "20");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test10021(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10021);
		body.put("noticeid", "12");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void test10022(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10022);
//		body.put("noticeid", "12");
		//点赞测试
//		body.put("commtype", "0");
//		body.put("commuserid", "1000119002");
		body.put("noticeid", "26");
		body.put("commtype", "1");
		body.put("commuserid", "1000119002");		
		body.put("commcontent", "测试哟哟!!!!!!!!");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//gaoyang say test
	@org.junit.Test
	public void test10016(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10016);
		body.put("sayuserid", "1000136admin");
		body.put("saycontent", "测试哈哈哈哈哈");
		body.put("permission", "2");
		body.put("sayusers", "1000119002;1000128admin;1000119admin");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void test10008(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10008);
		body.put("userId", "1000119002");
		body.put("sid", "310");
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test(JSONObject param) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(url);  
        JSONObject response = null;  
        try {  
            StringEntity s = new StringEntity(param.toString());  
            s.setContentType("application/json;charset=UTF-8");  
            post.setEntity(s);  
              
            HttpResponse res = client.execute(post);  
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
                HttpEntity entity = res.getEntity();  
                String content = EntityUtils.toString(entity);
                System.out.println(content);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
	}
	
	public void post(String url,JSONObject param) throws MalformedURLException, IOException, ClassNotFoundException{
		HttpURLConnection con=(HttpURLConnection)new URL(url).openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("charset", "UTF-8");
		con.setRequestProperty("Accept", "pplication/x-java-serialized-object");
		con.setRequestMethod("POST");
		ObjectOutputStream os=new ObjectOutputStream(con.getOutputStream());
		os.writeObject(param);
		os.flush();
		os.close();
		Map<String, List<String>> m=con.getHeaderFields();
//		InputStream is=con.getInputStream();
		ObjectInputStream is=new ObjectInputStream(con.getInputStream());
		Object obj=is.readObject();
		int length=0;
		byte[] tmp=new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((length=is.read(tmp))>0){
			bos.write(tmp, 0, length);
		}
		String result=new String(bos.toByteArray());
	}
	
	@org.junit.Test
	public void test10023(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10023);
		body.put("userId", "1000119002");		 
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	@org.junit.Test
	public void test10001(){
		JSONObject param=new JSONObject();
		JSONObject header=new JSONObject();
		JSONObject body=new JSONObject();
		header.put("service", 10001);
		body.put("type", "M");		 
		body.put("pwd", "123456");		 
		body.put("loginName", "18688925381");		 
		//body.put("userId", "1000119002");		 
		param.put("header", header);
		param.put("body", body);
		try {
			this.test(param);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	
	
}
