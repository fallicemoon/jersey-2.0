package com.jersey.userConfig.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityAttrMappingVO;
import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.tools.JerseyEnum.UserConfig;
import com.jersey.tools.Tools;

/**
 * 
 * @author fallicemoon
 *
 */
@Service
public class UserConfigService {
	
	@Autowired
	private UserConfigDAO userConfigDAO;
	@Autowired
	private CommodityTypeDAO commodityTypeDAO;
	@Autowired
	private CommodityAttrDAO commodityAttrDAO;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserSession userSession;
	
	private static final String COMMODITY_ATTR_VALUE_REG = "[\u4e00-\u9fa5_a-zA-Z\\d]{1,30}";
	
	//使用者相關登入資訊
	public void initAdminUserSessionUserConfig(String userName) {
		userSession.getUserConfigVO().setUserName(userName);
		userSession.setUserConfigVO(userConfigDAO.getByUserName(userName));
		initCommodityAttrMap();
	}
	
	public void initCustomerUserSessionUserConfig() {
		initCommodityAttrMap();
	}
	
	//-----------------------update config-----------------------
	public void updateUserConfig (UserConfig userConfig, Integer value) throws SQLException, IOException {
		userConfigDAO.updateUserConfig(userConfig, value);
		userSession.setUserConfigVO(userConfigDAO.getByUserName(userSession.getUserConfigVO().getUserName()));
	}
	
	public CommodityTypeVO createCommodityType (CommodityTypeVO commodityTypeVO) {
		CommodityTypeVO result = commodityTypeDAO.create(commodityTypeVO);
		initCommodityAttrMap();
		return result;
	}
	
	public void removeCommodityType (Integer id) {
		CommodityTypeVO commodityTypeVO = new CommodityTypeVO();
		commodityTypeVO.setCommodityTypeId(id);
		initCommodityAttrMap();
		if (!commodityTypeDAO.delete(commodityTypeVO)) {
			throw new RuntimeException();
		}
	}
	
	public void updateCommodityType (CommodityTypeVO commodityTypeVO) {
		commodityTypeDAO.update(commodityTypeVO);
		initCommodityAttrMap();
	}
	
	public CommodityAttrVO createCommodityAttr (CommodityAttrVO commodityAttrVO) {
		List<CommodityVO> list = commodityService.getAll(commodityAttrVO.getCommodityTypeVO());
		//要把所有商品都新增上此一屬性值
		Set<CommodityAttrMappingVO> commodityAttrMappingList = new HashSet<>();
		for (CommodityVO commodityVO : list) {
			CommodityAttrMappingVO commodityAttrMappingVO = new CommodityAttrMappingVO();
			commodityAttrMappingVO.setCommodityAttrVO(commodityAttrVO);
			commodityAttrMappingVO.setCommodityAttrValue("");
			commodityAttrMappingVO.setCommodityVO(commodityVO);
			commodityAttrMappingList.add(commodityAttrMappingVO);
		}
		commodityAttrVO.setCommodityAttrMappings(commodityAttrMappingList);
		CommodityAttrVO result = commodityAttrDAO.create(commodityAttrVO);
		initCommodityAttrMap();
		return result;
	}
	
	public void removeCommodityAttr (Integer id) {
		CommodityAttrVO commodityAttrVO = commodityAttrDAO.getOne(id);
		if (!commodityAttrDAO.delete(commodityAttrVO)) {
			throw new RuntimeException();
		}
		initCommodityAttrMap();
	}
	
	public void updateCommodityAttr (CommodityAttrVO commodityAttrVO) {
		commodityAttrDAO.update(commodityAttrVO);
		initCommodityAttrMap();
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
	
	public JSONObject validateCommodityType (CommodityTypeVO commodityTypeVO) {
		if (userSession.getCommodityTypeAttrStringMap().keySet().contains(commodityTypeVO.getCommodityType())) {
			return Tools.getFailJson("已經有此商品種類");
		}
		if (!commodityTypeVO.getCommodityType().matches(COMMODITY_ATTR_VALUE_REG)) {
			return Tools.getFailJson("商品種類長度為1~30,且只能有中文、英文、數字、底線");
		}
		return Tools.getSuccessJson();
	}
	
	public JSONObject validateCommodityAttr (CommodityAttrVO commodityAttrVO) {
		if (userSession.getCommodityTypeAttrStringMap().get(commodityAttrVO.getCommodityTypeVO().getCommodityType()).contains(commodityAttrVO.getCommodityAttr())) {
			return Tools.getFailJson("已經有此商品屬性");
		}
		if (!commodityAttrVO.getCommodityAttr().matches(COMMODITY_ATTR_VALUE_REG)) {
			return Tools.getFailJson("商品屬性長度為1~30,且只能有中文、英文、數字、底線");
		}
		return Tools.getSuccessJson();
	}
	
	/**
	 * 此方法不會過濾掉ADMIN_HIDDEN(ADMIN_HIDDEN只是頁面上看不到，理論上session裡面還是要有)
	 */
	private void initCommodityAttrMap () {
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
