package com.jersey.userConfig.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.jersey.tools.JerseyEnum.Authority;
import com.jersey.tools.JerseyEnum.UserConfig;

@Service
@SessionScope
public class UserConfigService {
	
	//clob不要複製, 怕太吃資源
	//private static final String[] CLOB_FIELDS = new String[]{"commodityAttr"};
	
	private UserConfigVO userConfigVO;
	private Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap;
	private Map<String, List<String>> commodityTypeAttrStringMap;
	
	@Autowired
	private UserConfigDAO userConfigDAO;
	@Autowired
	private CommodityTypeDAO commodityTypeDAO;
	@Autowired
	private CommodityAttrDAO commodityAttrDAO;
	
	@PostConstruct
	public void init () throws SQLException, IOException {
		userConfigVO = userConfigDAO.getByUserName("jersey");
		generateCommodityAttrMap();
	}

	//-----------------------get config-----------------------
	public Authority getAuthority () {
		return userConfigVO.getAuthority();
	}
	
	public Integer getCommodityPageSize () {
		return userConfigVO.getCommodityPageSize();
	}
	
	public Integer getPurchaseCasePageSize () {
		return userConfigVO.getPurchaseCasePageSize();
	}
	
	public Integer getSellCasePageSize () {
		return userConfigVO.getSellCasePageSize();
	}
	
	public Integer getStorePageSize () {
		return userConfigVO.getStorePageSize();
	}
	
	//取得key為商品類別, value為List<CommodityAttrVO>的map
	public Map<CommodityTypeVO, List<CommodityAttrVO>> getCommodityTypeAttrMap () {
		return commodityTypeAttrMap;
	}
	
	//方便用來檢查新增的商品種類和商品屬性是否有重複
	public Map<String, List<String>> getCommodityTypeAttrStringMap () {
		return commodityTypeAttrStringMap;
	} 
	
	
	//-----------------------update config-----------------------
	public void updateUserConfig (UserConfig userConfig, Integer value) throws SQLException, IOException {
		userConfigDAO.updateUserConfig(userConfig, value);
		init();
	}
	
	public void createCommodityType (CommodityTypeVO commodityTypeVO) {
		commodityTypeDAO.create(commodityTypeVO);
		generateCommodityAttrMap();
	}
	
	public void removeCommodityType (Integer[] ids) {
		commodityTypeDAO.delete(ids);
		generateCommodityAttrMap();
	}
	
	public void updateCommodityType (CommodityTypeVO commodityTypeVO) {
		commodityTypeDAO.update(commodityTypeVO);
		generateCommodityAttrMap();
	}
	
	public void createCommodityAttr (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.create(commodityAttrVO);
		generateCommodityAttrMap();
	}
	
	public void removeCommodityAttr (Integer id) {
		CommodityAttrVO commodityAttrVO = new CommodityAttrVO();
		commodityAttrVO.setCommodityAttrId(id);
		commodityAttrDAO.delete(commodityAttrVO);
		generateCommodityAttrMap();
	}
	
	public void updateCommodityAttrAuthority (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.update(commodityAttrVO);
		generateCommodityAttrMap();
	}
	
	//-----------------------commodity attr-----------------------
	public CommodityTypeVO getCommodityTypeVOByCommodityType(String commodityType){
		return commodityTypeDAO.getByCommodityType(commodityType);
	}
	public CommodityAttrVO getCommodityAttrVOByCommodityAttr(String commodityAttr){
		return commodityAttrDAO.getByCommodityAttrName(commodityAttr);
	}
	
	private void generateCommodityAttrMap () {
		commodityTypeAttrMap = new LinkedHashMap<>();
		commodityTypeAttrStringMap = new LinkedHashMap<>();
		for (CommodityTypeVO commodityTypeVO : commodityTypeDAO.getAll()) {
			List<CommodityAttrVO> commodityAttrList = commodityAttrDAO.getCommodityAttrByCommodityType(getAuthority(), commodityTypeVO);
			commodityTypeAttrMap.put(commodityTypeVO, commodityAttrList);
			List<String> commodityAttrStringList = new ArrayList<>();
			for (CommodityAttrVO commodityAttrVO : commodityAttrList) {
				commodityAttrStringList.add(commodityAttrVO.getCommodityAttr());
			}
			commodityTypeAttrStringMap.put(commodityTypeVO.getCommodityType(), commodityAttrStringList);
		}
	}

}
