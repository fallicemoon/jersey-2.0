package com.jersey.userConfig.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.tools.JerseyEnum.UserConfig;

/**
 * 請不要在此class注入userSession, 會導致循環注入
 * @author fallicemoon
 *
 */
@Service
public class UserConfigService {
	
	//clob不要複製, 怕太吃資源
	//private static final String[] CLOB_FIELDS = new String[]{"commodityAttr"};
	
	@Autowired
	private UserConfigDAO userConfigDAO;
	@Autowired
	private CommodityTypeDAO commodityTypeDAO;
	@Autowired
	private CommodityAttrDAO commodityAttrDAO;
	
	//使用者相關登入資訊
	public void initAdminUserSessionUserConfig(UserSession userSession, String userName) {
		userSession.setUserConfigVO(userConfigDAO.getByUserName(userName));
		initCommodityAttrMap(userSession);
	}
	
	public void initCustomerUserSessionUserConfig(UserSession userSession) {
		initCommodityAttrMap(userSession);
	}
	
	//-----------------------update config-----------------------
	public void updateUserConfig (UserConfig userConfig, Integer value) throws SQLException, IOException {
		userConfigDAO.updateUserConfig(userConfig, value);
	}
	
	public CommodityTypeVO createCommodityType (CommodityTypeVO commodityTypeVO) {
		CommodityTypeVO result = commodityTypeDAO.create(commodityTypeVO);
		return result;
	}
	
	public void removeCommodityType (Integer id) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		commodityTypeVO.setCommodityTypeId(id);
		commodityTypeDAO.delete(commodityTypeVO);
	}
	
	public void updateCommodityType (CommodityTypeVO commodityTypeVO) {
		commodityTypeDAO.update(commodityTypeVO);
	}
	
	public CommodityAttrVO createCommodityAttr (CommodityAttrVO commodityAttrVO) {
		CommodityAttrVO result = commodityAttrDAO.create(commodityAttrVO);
		return result;
	}
	
	public void removeCommodityAttr (Integer id) {
		CommodityAttrVO commodityAttrVO = new CommodityAttrVO();
		commodityAttrVO.setCommodityAttrId(id);
		commodityAttrDAO.delete(commodityAttrVO);
	}
	
	public void updateCommodityAttr (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.update(commodityAttrVO);
	}
	
	//-----------------------commodity attr-----------------------
	public CommodityTypeVO getCommodityTypeVOByCommodityTypeId(Integer commodityTypeId){
		return commodityTypeDAO.getOne(commodityTypeId);
	}
	
	public CommodityTypeVO getCommodityTypeVOByCommodityType(String commodityType){
		return commodityTypeDAO.getByCommodityType(commodityType);
	}
	
	public CommodityAttrVO getCommodityAttrVOByCommodityAttrId(Integer commodityAttrId){
		return commodityAttrDAO.getOne(commodityAttrId);
	}
	
	public CommodityAttrVO getCommodityAttrVOByCommodityAttr(String commodityAttr){
		return commodityAttrDAO.getByCommodityAttrName(commodityAttr);
	}
	
	private void initCommodityAttrMap (UserSession userSession) {
		Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap = new LinkedHashMap<>();
		Map<String, List<String>> commodityTypeAttrStringMap = new LinkedHashMap<>();
		for (CommodityTypeVO commodityTypeVO : commodityTypeDAO.getAll()) {
			List<CommodityAttrVO> commodityAttrList = commodityAttrDAO.getCommodityAttrByCommodityType(userSession.getUserConfigVO().getAuthority(), commodityTypeVO);
			commodityTypeAttrMap.put(commodityTypeVO, commodityAttrList);
			List<String> commodityAttrStringList = new ArrayList<>();
			for (CommodityAttrVO commodityAttrVO : commodityAttrList) {
				commodityAttrStringList.add(commodityAttrVO.getCommodityAttr());
			}
			commodityTypeAttrStringMap.put(commodityTypeVO.getCommodityType(), commodityAttrStringList);
		}
		userSession.setCommodityTypeAttrMap(commodityTypeAttrMap);
		userSession.setCommodityTypeAttrStringMap(commodityTypeAttrStringMap);
	}
	
	


}
