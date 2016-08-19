package com.jersey.userConfig.controller;

import java.util.Map;

import javax.websocket.server.PathParam;

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
import com.jersey.userConfig.model.UserConfigService;

@Controller
@RequestMapping("/userConfig")
public class UserConfigController {
	
	private static final String SYSTEM_PARAM = "userConfig/systemParam";
	private static final String COMMODITY_ATTR = "userConfig/commodityAttr";
	
	@Autowired
	private UserConfigService userConfigService;
	
	
	
	@RequestMapping(value="/systemParam", method=RequestMethod.GET)
	public String getSystemParam (Map<String, Object> map) {
		map.put(UserConfig.commodityPageSize.toString(), userConfigService.getCommodityPageSize());
		map.put(UserConfig.purchaseCasePageSize.toString(), userConfigService.getPurchaseCasePageSize());
		map.put(UserConfig.sellCasePageSize.toString(), userConfigService.getSellCasePageSize());
		map.put(UserConfig.storePageSize.toString(), userConfigService.getStorePageSize());
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
	
	@RequestMapping(value="/commodityAttr", method=RequestMethod.GET)
	public String getCommodityAttr (Map<String, Object> map) {
		CommodityAttrAuthority.admin.getShowName();
		map.put("commodityAttrAuthorityList", CommodityAttrAuthority.values());
		map.put("commodityAttrMap", userConfigService.getCommodityAttrMap());
		return COMMODITY_ATTR;
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
	public String createCommodityType (@RequestBody CommodityAttrVO commodityAttrVO) {
		try {
			if (userConfigService.getCommodityAttrMap().keySet().contains(commodityAttrVO.getCommodityTypeVO().getCommodityType())) {
				return Tools.getFailJson("已經有此商品類別").toString();
			}
			userConfigService.createCommodityAttr(commodityAttrVO);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			return Tools.getFailJson("新增商品類別失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr/{commodityType}", method=RequestMethod.POST)
	public String createCommodityAttr (@RequestBody CommodityAttrVO commodityAttrVO, @PathParam("commodityType") String commodityType) {
		try {
			if (!commodityType.equals(commodityAttrVO.getCommodityTypeVO().getCommodityType())) {
				return Tools.getFailJson("別亂來!").toString();
			}
			if (userConfigService.getCommodityAttrMap().keySet().contains(commodityType)) {
				return Tools.getFailJson("已經有此商品類別").toString();
			}
			userConfigService.createCommodityAttr(commodityAttrVO);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			return Tools.getFailJson("新增商品屬性失敗").toString();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/commodityAttr/{commodityType}", method=RequestMethod.DELETE, produces="application/json;charset=UTF-8")
	public String removeCommodityAttr (@RequestBody String request, @PathParam("commodityType") String commodityType, Map<String, Object> map) {
		
		return Tools.getSuccessJson().toString();
	}

}
