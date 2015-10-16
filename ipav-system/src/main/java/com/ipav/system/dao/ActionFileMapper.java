package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavActionFileEntity;

public interface ActionFileMapper {
    //插入文件信息
	public void insertActionfile(IpavActionFileEntity sayimage);
    //得到文件名称集合
	public List<String> queryActionfileList(Map map);
    //根据文件名称得到文件
	public IpavActionFileEntity queryActionfileByName(String imagename);
	//根据文件名称得到文件
	public IpavActionFileEntity queryActionfileByPath(String path);
	//根据文件类型id得到文件
	public IpavActionFileEntity queryActionfileByAction(Map map);
	//得到文件路径集合
	public List<String> queryActionfileUrlList(Map map);
    //修改文件路径
	public void updateFilePath(Map map);
}
