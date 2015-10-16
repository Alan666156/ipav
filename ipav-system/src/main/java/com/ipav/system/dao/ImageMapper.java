package com.ipav.system.dao;

import java.util.List;

import com.ipav.system.entity.IpavimageEntity;

public interface ImageMapper {
	
	public List<IpavimageEntity> getImageList();
	
	public Integer getImageListCount(IpavimageEntity image);
	
	public void addCompanyImage(IpavimageEntity image);
	
	public void updateCompanyImage(IpavimageEntity image);
	
	public IpavimageEntity getimageBykey(IpavimageEntity image);
}
