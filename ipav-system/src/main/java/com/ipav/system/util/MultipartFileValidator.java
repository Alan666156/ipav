package com.ipav.system.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传验证
 * @author GaoYang
 *
 */
public class MultipartFileValidator {
	
	/**
	 * 默认的上传文件大小1M
	 */
	private final static  long MAX_SIZE=1024*1024;  
	
	/** 
     * 文件大小上限 
     */  
    private long maxSize = MAX_SIZE;  
    
    /** 
     * 可接受的文件content-type 
     */  
    private String[] allowedContentTypes;

    
    /**
     * 设置上传文件上限值
     * @param maxSize
     */
	public void setMaxSize(long maxSize) {
		this.maxSize = MAX_SIZE*maxSize;
	}

	/**
	 * 设置上传文件类型
	 * @param allowedContentTypes
	 */
	public void setAllowedContentTypes(String[] allowedContentTypes) {
		this.allowedContentTypes = allowedContentTypes;
	}
    
	/**
	 * 文件校验
	 * @throws Exception 
	 */
    public void validate(MultipartFile file) throws Exception{
    	
    	if(file != null && !file.isEmpty()){    		
    		
    		//1.检查文件大小
    		if(file.getSize()<=0)
    			throw new Exception("The multipart file is null!");
    		
    		if(file.getSize()>maxSize)
    			throw new MaxUploadSizeExceededException(maxSize);
    		
    		//2.检查文件类型
    		String filename=file.getOriginalFilename();
    		String extensionName = filename.substring(filename.lastIndexOf("."), filename.length()).toLowerCase();
    		if(!ArrayUtils.contains(allowedContentTypes, extensionName))
    			throw new Exception("The content type '"+file.getContentType()+"'is not a valid content type !");
    	}
    }
}
