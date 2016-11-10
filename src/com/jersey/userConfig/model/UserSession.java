package com.jersey.userConfig.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.jersey.tools.JerseyEnum.Authority;

@Component
@SessionScope
public class UserSession{
	
	@Autowired
	CommodityTypeDAO commodityTypeDAO;
	@Autowired
	CommodityAttrDAO commodityAttrDAO;
	
	private UserConfigVO userConfigVO;
	private Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap;
	private Map<String, List<String>> commodityTypeAttrStringMap;
	
	//初始化為未登入的customer
	@PostConstruct
	public void init () {
		userConfigVO = new UserConfigVO();
		userConfigVO.setAuthority(Authority.CUSTOMER);
		userConfigVO.setCommodityPageSize(50);
		this.initCustomerCommodityAttrMap();
	} 
	
	public boolean isAdmin() {
		return userConfigVO.getAuthority()==Authority.ADMIN;
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
	
	private void initCustomerCommodityAttrMap () {
		Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap = new LinkedHashMap<>();
		Map<String, List<String>> commodityTypeAttrStringMap = new LinkedHashMap<>();
		for (CommodityTypeVO commodityTypeVO : commodityTypeDAO.getAll(Authority.CUSTOMER)) {
			List<CommodityAttrVO> commodityAttrList = commodityAttrDAO.getCommodityAttrByCommodityType(Authority.CUSTOMER, commodityTypeVO);
			commodityTypeAttrMap.put(commodityTypeVO, commodityAttrList);
			List<String> commodityAttrStringList = new ArrayList<>();
			for (CommodityAttrVO commodityAttrVO : commodityAttrList) {
				commodityAttrStringList.add(commodityAttrVO.getCommodityAttr());
			}
			commodityTypeAttrStringMap.put(commodityTypeVO.getCommodityType(), commodityAttrStringList);
		}
		this.setCommodityTypeAttrMap(commodityTypeAttrMap);
		this.setCommodityTypeAttrStringMap(commodityTypeAttrStringMap);
	}

}
