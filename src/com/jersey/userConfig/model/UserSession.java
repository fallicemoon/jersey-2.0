package com.jersey.userConfig.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.jersey.tools.JerseyEnum.Authority;

@Component
@SessionScope
public class UserSession{
	
	private UserConfigVO userConfigVO;
	private Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap;
	private Map<String, List<String>> commodityTypeAttrStringMap;
	
	public boolean isAdmin() {
		return userConfigVO.getAuthority()==Authority.admin;
	}
	public UserConfigVO getUserConfigVO() {
		return userConfigVO;
	}
	public void setUserConfigVO(UserConfigVO userConfigVO) {
		this.userConfigVO = userConfigVO;
	}
	public Map<CommodityTypeVO, List<CommodityAttrVO>> getCommodityTypeAttrMap() {
		return commodityTypeAttrMap;
	}
	public void setCommodityTypeAttrMap(Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap) {
		this.commodityTypeAttrMap = commodityTypeAttrMap;
	}
	public Map<String, List<String>> getCommodityTypeAttrStringMap() {
		return commodityTypeAttrStringMap;
	}
	public void setCommodityTypeAttrStringMap(Map<String, List<String>> commodityTypeAttrStringMap) {
		this.commodityTypeAttrStringMap = commodityTypeAttrStringMap;
	}
	
	

}
