package com.jersey.userConfig.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.jersey.tools.JerseyEnum.UserConfig;

@Service
@SessionScope
public class UserConfigService {
	
	//clob不要複製, 怕太吃資源
	//private static final String[] CLOB_FIELDS = new String[]{"commodityAttr"};
	
	private UserConfigVO userConfigVO;
	private Map<String, List<CommodityAttrVO>> commodityAttrMap;
	
	@Autowired
	private UserConfigDAO userConfigDAO;
	@Autowired
	private CommodityAttrDAO commodityAttrDAO;
	
	@PostConstruct
	public void init () throws SQLException, IOException {
		//目前設定檔只有一筆, PK為1
		userConfigVO = userConfigDAO.getOne(1);
		generateCommodityAttrMap();
	}

	//-----------------------get config-----------------------
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
	public Map<String, List<CommodityAttrVO>> getCommodityAttrMap () {
		return commodityAttrMap;
	}
	
	
	//-----------------------update config-----------------------
	public void updateUserConfig (UserConfig userConfig, Integer value) throws SQLException, IOException {
		userConfigDAO.updateUserConfig(userConfig, value);
		init();
	}
	
	public void createCommodityAttr (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.create(commodityAttrVO);
		generateCommodityAttrMap();
	} 
	
	public void removeCommodityAttr (Integer[] ids) {
		commodityAttrDAO.delete(ids);
		generateCommodityAttrMap();
	}
	
	public void updateCommodityAttrAuthority (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.update(commodityAttrVO);
		generateCommodityAttrMap();
	}
	
	//-----------------------commodity attr-----------------------
	private void generateCommodityAttrMap () {
		commodityAttrMap = new LinkedHashMap<>();
		for (String commodityType : commodityAttrDAO.getCommodityTypeList()) {
			commodityAttrMap.put(commodityType, commodityAttrDAO.getCommodityAttrList(commodityType));
		}
	}

}
