package com.jersey.userConfig.controller;

import java.util.Map;

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
	@RequestMapping(value = "/systemParam/pageSize/{type}", method=RequestMethod.PUT)
	public String updateSystemParam (@PathVariable("type") String type, @RequestBody String[] values) throws InterruptedException {
		try {
			UserConfig userConfig = UserConfig.valueOf(type);
			Integer value = Integer.valueOf(values[0]);
			if (value<=0 || value>100) {
				throw new Exception("分頁筆數不得小於0或大於100");
			}
			userConfigService.updateUserConfig(userConfig, value);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson().toString();
		}
	}
	
	@RequestMapping(value="/commodityAttr", method=RequestMethod.GET)
	public String getCommodityAttr (Map<String, Object> map) {
		map.put("commodityAttrAuthority", CommodityAttrAuthority.values());
		map.put("commodityAttrMap", userConfigService.getCommodityAttr());
		return COMMODITY_ATTR;
	}

}
