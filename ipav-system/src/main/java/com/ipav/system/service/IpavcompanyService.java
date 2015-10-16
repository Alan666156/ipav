package com.ipav.system.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ipav.system.dao.CompanyMapper;
import com.ipav.system.dao.ImageMapper;
import com.ipav.system.entity.IpavcomatteEntity;
import com.ipav.system.entity.IpavcompanyEntity;
import com.ipav.system.entity.IpavimageEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.ImageDrawUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.ftp.FTPUtil;

/**
 * creater Jerry All right reserved. Created on 2014年11月19日 下午3:55:38
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavcompanyService {
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ImageMapper imageMapper;

	/**
	 * 更新企业信息
	 * @param company
	 */
	public void updateCompany(IpavcompanyEntity company) {
		companyMapper.updateCompany(company);
	}
	
	/**
	 * 查询企业列表集合
	 * @param map
	 * @return
	 */
	public List<IpavcompanyEntity> queryCompanyList(Map map){
		return companyMapper.queryCompanyList(map);
	}
	
	/**
	 * 根据企业id查询企业
	 * @param companyid
	 * @return
	 */
	public IpavcompanyEntity queryCompanyByid(String companyid) {
		return companyMapper.queryCompanyByid(companyid);
	}
	
	/**
	 * 更新企业认证状态
	 * @param comatte
	 * @param passflg
	 */
	@Transactional
	public void modifyCompanyAtte(IpavcomatteEntity comatte,String passflg){
		String dt = FormatUtil.formatDate(new Date(), null);
		Map parm = new HashMap();
		parm.put("companyid", comatte.getCompanyid());
		parm.put("attstuts",passflg);
		if("1".equals(passflg)){
			parm.put("attetime", dt);
		}else{
			comatte.setAttedate(dt);
			comatte.setAtteuser(ContentUtil.DEFAULT_USER_NAME);
			companyMapper.insertCompanyAtte(comatte);
			parm.put("attetime", "");
		}	
		companyMapper.updateCompanyAtte(parm);	
	}
	
	/**
	 * 保存企业LOGO
	 * @param filefield LOGO图片
	 * @param map
	 * @throws Exception
	 */
	@Transactional
	public void saveCompanyLogo(MultipartFile filefield,Map map)throws Exception{
		String sorcepath="";
		String subpath="";
		String filetype="";
		String dirpath=ContentUtil.IMAGEPATHS.get("company");
		String companyid = (String)map.get("companyid");
		IpavimageEntity image = new IpavimageEntity();
		image.setCompanyid(Long.parseLong(companyid));
		image.setBustype("LOGO");
		image.setCreater("system");
		IpavimageEntity oldimage = imageMapper.getimageBykey(image);
		if(oldimage!=null) {
			sorcepath=oldimage.getSorcepath();
		}
		if(!filefield.isEmpty()&&filefield!=null){
			deleteOldImage(oldimage);
			FTPUtil ftp=new FTPUtil();
			// 获取文件原名
			String oldfilename = filefield.getOriginalFilename();
			// 获取文件的扩展名
			filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1, oldfilename.length());
			// 新的文件路径名 = 获取时间戳+"."文件扩展名
			sorcepath = System.currentTimeMillis()+ "." + filetype;
			//删除,防止重复上传
			ftp.delete(sorcepath,dirpath);
			// 执行保存在服务器
//			BufferedImage bi=ImageDrawUtil.resize(filefield.getInputStream(),410, 213);
			System.out.println(filefield.getOriginalFilename());
			ftp.upload(filefield.getInputStream(),dirpath, sorcepath);
		}
			
		if(!"".equals(sorcepath)){
			subpath =System.currentTimeMillis()+"."+ sorcepath.substring(sorcepath.lastIndexOf(".")+1, sorcepath.length());
			//获取上传到ftp服务器的图片
			URL url=new URL(ContentUtil.IMAGE_ROOT+dirpath+sorcepath);
			//按照前台比例先将图片压缩
			BufferedImage bi=ImageDrawUtil.resize(url.openConnection().getInputStream(),410, 213);
			map.put("pname", subpath);
			//然后将压缩后的图片剪切
			ImageDrawUtil.cutPicture(bi, map,dirpath);
		}
		image.setSorcepath(sorcepath);
		image.setSubpath(subpath);
		image.setCreatdate(FormatUtil.formatDate(new Date(), null));
		int count= imageMapper.getImageListCount(image);
		if(count>0){
			imageMapper.updateCompanyImage(image);
		}else{
			imageMapper.addCompanyImage(image);
		}
	}
	
	/**
	 * 保存企业认证图片
	 * @param filefield
	 * @param map
	 * @throws Exception
	 */
	public void saveCompanyAtte(MultipartFile filefield,Map map)throws Exception {
		if(!filefield.isEmpty()&&filefield!=null){
			String companyid = (String)map.get("companyid");
			IpavimageEntity image = new IpavimageEntity();
			image.setCompanyid(Long.parseLong(companyid));
			image.setBustype("ATTE");
			image.setCreater("system");
			IpavimageEntity oldimage = imageMapper.getimageBykey(image);
			deleteOldImage(oldimage);
			String sorcepath =ImageUtil.saveImage(filefield, ContentUtil.IMAGEPATHS.get("company"));
			image.setSorcepath(sorcepath);
			image.setSubpath(sorcepath);
			image.setCreatdate(FormatUtil.formatDate(new Date(), null));
			int count= imageMapper.getImageListCount(image);
			if(count>0){
				imageMapper.updateCompanyImage(image);
			}else{
				imageMapper.addCompanyImage(image);
			}
			//企业认证图片上传后,修改企业认证状态为待认证--------Gaoyang
			Map parm = new HashMap();
			parm.put("companyid", companyid);
			parm.put("attstuts", "2");
			companyMapper.updateCompanyAtte(parm);
		}
	}
	/**
	 * 图片传输方式该为FTP文件传输,此方法废除
	 * 获取验证图片
	 * @param companyid
	 * @param resp
	 * @throws Exception
	 */
	public void getcompanyAtte(String companyid ,HttpServletResponse resp)throws Exception{
		if(StringUtils.isNotEmpty(companyid)){
			IpavimageEntity image = new IpavimageEntity();
			image.setCompanyid(Long.parseLong(companyid));
			image.setBustype("ATTE");
			IpavimageEntity temp= imageMapper.getimageBykey(image);
			InputStream is = null;  
			is =new FileInputStream(new File(temp.getSorcepath()));  
			
	        BufferedImage sorce=ImageIO.read(is);
			// 禁止图像缓存。 
			resp.setHeader("Pragma", "no-cache"); 
			resp.setHeader("Cache-Control", "no-cache"); 
			resp.setDateHeader("Expires", 0); 
			resp.setContentType("image/jpeg"); 
			// 将图像输出到Servlet输出流中。 
			try {
				ServletOutputStream sos = resp.getOutputStream(); 
				ImageIO.write(sorce, "jpeg", sos); 
				sos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 图片传输方式该为FTP文件传输,此方法废除
	 * 查看公司logo图片
	 * @param companyid
	 * @param resp
	 * @throws Exception
	 */
	public void companyLogo(String companyid,String pty ,HttpServletResponse resp)throws Exception{
		if(StringUtils.isNotEmpty(companyid)){
			IpavimageEntity image = new IpavimageEntity();
			image.setCompanyid(Long.parseLong(companyid));
			image.setBustype("LOGO");
			IpavimageEntity temp= imageMapper.getimageBykey(image);
			InputStream is = null;  
			if("S".equals(pty)){
				is =new FileInputStream(new File(temp.getSorcepath()));  
			}else if("B".equals(pty)){
				is =new FileInputStream(new File(temp.getSubpath()));  
			}
			
	        BufferedImage sorce=ImageIO.read(is);
			// 禁止图像缓存。 
			resp.setHeader("Pragma", "no-cache"); 
			resp.setHeader("Cache-Control", "no-cache"); 
			resp.setDateHeader("Expires", 0); 
			resp.setContentType("image/jpeg"); 
			// 将图像输出到Servlet输出流中。 
			try {
				ServletOutputStream sos = resp.getOutputStream(); 
				ImageIO.write(sorce, "jpeg", sos); 
				sos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据企业id获取企业图片信息
	 * @param companyId 企业id
	 * @param busType 图片类型包括:LOGO和ATTE(认证)
	 * @return
	 */
	public IpavimageEntity getCompanyImage(long companyId,String busType){
		IpavimageEntity image = new IpavimageEntity();
		image.setCompanyid(companyId);
		image.setBustype(busType);
		return imageMapper.getimageBykey(image);
	}

	/**
	 * 获取所有省份列表
	 * @return
	 */
	public List<Map<String, Object>> getProvince() {
		return companyMapper.queryProvince();
	}

	/**
	 * 获取所有城市列表
	 * @param province_id
	 * @return
	 */
	public List<Map<String, Object>> getCitys(String province_id) {
		return companyMapper.queryCitys(province_id);
	}

	/**
	 * 获取企业列表数量
	 * @param parm
	 * @return
	 */
	public int getCompanySize(Map parm) {
		return companyMapper.getCompanySize(parm)==null?0:companyMapper.getCompanySize(parm);
	}

	/**
	 *删除ftp服务器上公司原有的图片文件
	 * @author GaoYang
	 * @param oldimage
	 */
	private void deleteOldImage(IpavimageEntity oldimage){
		if(oldimage!=null){
			FTPUtil ftp = new FTPUtil();
			String oldsorcepath = oldimage.getSorcepath();
			String oldpubpath = oldimage.getSubpath();
			if(StringUtils.isNotEmpty(oldsorcepath)&&!ContentUtil.DEFUAL_LOGO_PIC.equals(oldsorcepath))
				ftp.delete(oldsorcepath, ContentUtil.IMAGEPATHS.get("company"));
			if(StringUtils.isNotEmpty(oldpubpath)&&!ContentUtil.DEFUAL_SUB_LOGO_PIC.equals(oldpubpath))
				ftp.delete(oldpubpath, ContentUtil.IMAGEPATHS.get("company"));					
		}
	}
	
	/**
	 * 查询企业认证结果信息 
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryCompanyAtte(Map map) {
		return companyMapper.queryCompanyAtte(map);
	}
	
	/**
	 * 查询企业类型列表
	 * @param type
	 * @return
	 */
	public List<Map> queryPparameterByType(String type) {
		return companyMapper.queryPparameterByType(type);
	}
}
