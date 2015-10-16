package com.ipav.system.entity;

import java.io.Serializable;

/***
 * 
 * @author Jerry
 *
 */
public class IpavmenuEntity implements Serializable {
	private static final long serialVersionUID = 4014284208451991654L;

	private Long menuid ;
	private String menuname;
	private String pathstr;
	private Integer levl;
	private Long parentid;
	private Long rootid;
	private String valflg;
	private String createdate;
	private String creater;
	private String imgsrc;
	private String delflg;
	
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public Long getRootid() {
		return rootid;
	}
	public void setRootid(Long rootid) {
		this.rootid = rootid;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getPathstr() {
		return pathstr;
	}
	public void setPathstr(String pathstr) {
		this.pathstr = pathstr;
	}
	public Integer getLevl() {
		return levl;
	}
	public void setLevl(Integer levl) {
		this.levl = levl;
	}
	public String getValflg() {
		return valflg;
	}
	public void setValflg(String valflg) {
		this.valflg = valflg;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}


}
