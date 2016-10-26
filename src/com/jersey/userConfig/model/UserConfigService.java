package com.jersey.userConfig.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityAttrMappingDAO;
import com.jersey.commodity.model.CommodityAttrMappingVO;
import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.JerseyEnum.UserConfig;
import com.jersey.tools.Tools;

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
	@Autowired
	private CommodityAttrMappingDAO commodityAttrMappingDAO;
	@Autowired
	private CommodityService commodityService;
	
	private static final String COMMODITY_ATTR_VALUE_REG = "[a-zA-Z\\d]*";
	
	//使用者相關登入資訊
	public void initAdminUserSessionUserConfig(UserSession userSession, String userName) {
		userSession.getUserConfigVO().setUserName(userName);
		userSession.setUserConfigVO(userConfigDAO.getByUserName(userName));
		initCommodityAttrMap(userSession);
	}
	
	public void initCustomerUserSessionUserConfig(UserSession userSession) {
		initCommodityAttrMap(userSession);
	}
	
	//-----------------------update config-----------------------
	public void updateUserConfig (UserSession userSession, UserConfig userConfig, Integer value) throws SQLException, IOException {
		userConfigDAO.updateUserConfig(userConfig, value);
		userSession.setUserConfigVO(userConfigDAO.getByUserName(userSession.getUserConfigVO().getUserName()));
	}
	
	public CommodityTypeVO createCommodityType (UserSession userSession, CommodityTypeVO commodityTypeVO) {
		CommodityTypeVO result = commodityTypeDAO.create(commodityTypeVO);
		initCommodityAttrMap(userSession);
		return result;
	}
	
	public void removeCommodityType (UserSession userSession, Integer id) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		commodityTypeVO.setCommodityTypeId(id);
		initCommodityAttrMap(userSession);
		if (!commodityTypeDAO.delete(commodityTypeVO)) {
			throw new RuntimeException();
		}
	}
	
	public void updateCommodityType (UserSession userSession, CommodityTypeVO commodityTypeVO) {
		commodityTypeDAO.update(commodityTypeVO);
		initCommodityAttrMap(userSession);
	}
	
	public CommodityAttrVO createCommodityAttr (UserSession userSession, CommodityAttrVO commodityAttrVO) {
		CommodityAttrVO result = commodityAttrDAO.create(commodityAttrVO);
		//要把所有商品都新增上此一屬性值
		List<CommodityVO> list = commodityService.getAll(commodityAttrVO.getCommodityTypeVO());
		List<CommodityAttrMappingVO> commodityAttrMappingList = new ArrayList<>();
		for (CommodityVO commodityVO : list) {
			CommodityAttrMappingVO commodityAttrMappingVO = new CommodityAttrMappingVO();
			commodityAttrMappingVO.setCommodityAttrVO(commodityAttrVO);
			commodityAttrMappingVO.setCommodityAttrValue("");
			commodityAttrMappingVO.setCommodityVO(commodityVO);
			commodityAttrMappingVO.setCreateTime(new Date());
			commodityAttrMappingVO.setLastModifyTime(new Date());
			commodityAttrMappingList.add(commodityAttrMappingVO);
		}
		commodityAttrMappingDAO.create(commodityAttrMappingList);
		initCommodityAttrMap(userSession);
		return result;
	}
	
	public void removeCommodityAttr (UserSession userSession, Integer id) {
		CommodityAttrVO commodityAttrVO = commodityAttrDAO.getOne(id);
		if (!commodityAttrDAO.delete(commodityAttrVO)) {
			throw new RuntimeException();
		}
		initCommodityAttrMap(userSession);
	}
	
	public void updateCommodityAttr (UserSession userSession, CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.update(commodityAttrVO);
		initCommodityAttrMap(userSession);
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
	
	public JSONObject validateCommodityType (UserSession userSession, CommodityTypeVO commodityTypeVO) {
		if (userSession.getCommodityTypeAttrStringMap().keySet().contains(commodityTypeVO.getCommodityType())) {
			return Tools.getFailJson("已經有此商品種類");
		}
		if (!commodityTypeVO.getCommodityType().matches(COMMODITY_ATTR_VALUE_REG)) {
			return Tools.getFailJson("商品種類只能有英文或數字");
		}
		return Tools.getSuccessJson();
	}
	
	public JSONObject validateCommodityAttr (UserSession userSession, CommodityAttrVO commodityAttrVO) {
		if (userSession.getCommodityTypeAttrStringMap().get(commodityAttrVO.getCommodityTypeVO().getCommodityType()).contains(commodityAttrVO.getCommodityAttr())) {
			return Tools.getFailJson("已經有此商品屬性");
		}
		if (!commodityAttrVO.getCommodityAttr().matches(COMMODITY_ATTR_VALUE_REG)) {
			return Tools.getFailJson("商品屬性只能有英文或數字或空白");
		}
		return Tools.getSuccessJson();
	}
	
	
	private void initCommodityAttrMap (UserSession userSession) {
		Map<CommodityTypeVO, List<CommodityAttrVO>> commodityTypeAttrMap = new LinkedHashMap<>();
		Map<String, List<String>> commodityTypeAttrStringMap = new LinkedHashMap<>();
		for (CommodityTypeVO commodityTypeVO : commodityTypeDAO.getAll(userSession.getUserConfigVO().getAuthority())) {
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
