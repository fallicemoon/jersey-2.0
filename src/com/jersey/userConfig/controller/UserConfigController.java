package com.jersey.userConfig.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.tools.JerseyEnum.CommodityAttrAuthority;
import com.jersey.tools.JerseyEnum.UserConfig;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.CommodityAttrVO;
import com.jersey.userConfig.model.CommodityTypeVO;
import com.jersey.userConfig.model.UserConfigService;
import com.jersey.userConfig.model.UserSession;

@Controller
@RequestMapping("/userConfig")
public class UserConfigController {
	
	private static final String SYSTEM_PARAM = "userConfig/systemParam";
	private static final String COMMODITY_ATTR = "userConfig/commodityAttr";
	
	
	@Autowired
	private UserConfigService userConfigService;
	@Autowired
	private UserSession userSession;
	
	@RequestMapping(value="/systemParam", method=RequestMethod.GET)
	public String getSystemParam (Map<String, Object> map) {
		map.put(UserConfig.COMMODITY_PAGE_SIZE.toString(), userSession.getUserConfigVO().getCommodityPageSize());
		map.put(UserConfig.PURCHASE_CASE_PAGE_SIZE.toString(), userSession.getUserConfigVO().getPurchaseCasePageSize());
		map.put(UserConfig.SELL_CASE_PAGE_SIZE.toString(), userSession.getUserConfigVO().getSellCasePageSize());
		map.put(UserConfig.STORE_PAGE_SIZE.toString(), userSession.getUserConfigVO().getStorePageSize());
		return SYSTEM_PARAM;
	}
	
	@ResponseBody
	@RequestMapping(value = "/systemParam/pageSize/{type}", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String updateSystemParam (@PathVariable("type") String type, @RequestBody String[] values) throws InterruptedException {
		try {
			UserConfig userConfig = UserConfig.valueOf(type);
			Integer value = Integer.valueOf(values[0]);
			if (value<=0 || value>100) {
				return Tools.getFailJson("分頁筆數不得小於0或大於100").toString();
			}
			userConfigService.updateUserConfig(userConfig, value);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("修改參數失敗").toString();
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------
	@RequestMapping(value="/commodityAttr", method=RequestMethod.GET)
	public String getCommodityAttr (Map<String, Object> map) {
		map.put("commodityAttrMap", userSession.getCommodityTypeAttrMap());
		return COMMODITY_ATTR;
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityType", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String createCommodityType (@RequestBody CommodityTypeVO commodityTypeVO) {
		try {
			JSONObject json = userConfigService.validateCommodityType(commodityTypeVO);
			if ("fail".equals(json.getString("result"))) {
				return json.toString();
			}
			commodityTypeVO = userConfigService.createCommodityType(commodityTypeVO);
			return Tools.getSuccessJson().put("commodityTypeId", commodityTypeVO.getCommodityTypeId()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("新增商品類別失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr/{commodityTypeId}", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String createCommodityAttr (@RequestBody CommodityAttrVO commodityAttrVO, @PathVariable("commodityTypeId") Integer commodityTypeId) {
		try {
			CommodityTypeVO commodityTypeVO = userConfigService.getCommodityTypeVOByCommodityTypeId(commodityTypeId);
			commodityAttrVO.setCommodityTypeVO(commodityTypeVO);
			JSONObject json = userConfigService.validateCommodityAttr(commodityAttrVO);
			if ("fail".equals(json.getString("result"))) {
				return json.toString();
			}
			
			CommodityAttrVO result = userConfigService.createCommodityAttr(commodityAttrVO);
			return Tools.getSuccessJson()
					.put("commodityAttrId", result.getCommodityAttrId())
					.put("commodityType", result.getCommodityTypeVO().getCommodityType())
					.put("commodityAttr", result.getCommodityAttr())
					.put("commodityAttrAuthority", result.getCommodityAttrAuthority())
					.put("commodityAttrAuthorityShowName", result.getCommodityAttrAuthority().getShowName())
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("新增商品屬性失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityType/{commodityTypeId}", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String updateCommodityType(@RequestBody String[] commodityType, @PathVariable("commodityTypeId") Integer commodityTypeId){
		try {
			CommodityTypeVO commodityTypeVO = userConfigService.getCommodityTypeVOByCommodityTypeId(commodityTypeId);
			String oldCommodityType = commodityTypeVO.getCommodityType();
			commodityTypeVO.setCommodityType(commodityType[0]);
			
			//如果新輸入的商品種類名稱和舊的不一樣就要檢查此商品種類名稱是否存在
			if (!commodityTypeVO.getCommodityType().equals(oldCommodityType)) {
				JSONObject json = userConfigService.validateCommodityType(commodityTypeVO);
				if ("fail".equals(json.getString("result"))) {
					return json.toString();
				}
			}
			
			userConfigService.updateCommodityType(commodityTypeVO);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("更新商品類別失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr/{commodityAttrId}", method=RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String updateCommodityAttr(@RequestBody String[] commodityAttrArray, @PathVariable("commodityAttrId") Integer commodityAttrId){
		try {
			String newCommodityAttr = commodityAttrArray[0];
			CommodityAttrAuthority commodityAttrAuthority = CommodityAttrAuthority.valueOf(commodityAttrArray[1]);
			CommodityAttrVO commodityAttrVO = userConfigService.getCommodityAttrVOByCommodityAttrId(commodityAttrId);
			String oldCommodityAttr = commodityAttrVO.getCommodityAttr();
			
			commodityAttrVO.setCommodityAttrId(commodityAttrId);
			commodityAttrVO.setCommodityAttr(newCommodityAttr);
			commodityAttrVO.setCommodityAttrAuthority(commodityAttrAuthority);
			
			//如果新輸入的商品屬性名稱和舊的不一樣就要檢查此商品屬性名稱是否存在
			if (!commodityAttrVO.getCommodityAttr().equals(oldCommodityAttr)) {
				JSONObject json = userConfigService.validateCommodityAttr(commodityAttrVO);
				if ("fail".equals(json.getString("result"))) {
					return json.toString();
				}
			}
			
			userConfigService.updateCommodityAttr(commodityAttrVO);
			return Tools.getSuccessJson().put("commodityAttrAuthorityShowName", commodityAttrAuthority.getShowName()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("更新商品屬性失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr/{commodityAttrId}", method=RequestMethod.DELETE, produces="application/json;charset=UTF-8")
	public String removeCommodityAttr (@PathVariable("commodityAttrId") Integer commodityAttrId, Map<String, Object> map) {
		try {
			userConfigService.removeCommodityAttr(commodityAttrId);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除商品屬性失敗").toString();
		}		
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityType/{commodityTypeId}", method=RequestMethod.DELETE, produces="application/json;charset=UTF-8")
	public String removeCommodityType (@PathVariable("commodityTypeId") Integer commodityTypeId, Map<String, Object> map) {
		try {
			userConfigService.removeCommodityType(commodityTypeId);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除商品種類失敗").toString();
		}		
	}

	
	
}
