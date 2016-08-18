package com.jersey.commodity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.commodity.model.CommodityDisplay;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.UserConfigService;

@Controller
@RequestMapping(value = "/commodity")
public class CommodityController {
	private static final String LIST = "commodity/list";
	private static final String ADD = "commodity/add";
	private static final String UPDATE = "commodity/update";
	private static final String REDIRECT_LIST = "redirect:getAll";

	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserConfigService userConfigService;

	// for update commodity用
	@ModelAttribute
	public void getCommodity(Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if (keySet.contains("id")) {
			String storeId = pathVariableMap.get("id");
			map.put("commodity", commodityService.getOne(Integer.valueOf(storeId)));
		}
	}

	// 取得全部
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(Map<String, Object> map,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		map.put("commodityList", commodityService.getAll(userConfigService.getCommodityPageSize(), page, userConfigService.getAuthority()));
		map.put("pages", commodityService.getPages());
		return LIST;
	}

	// 準備新增
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String get(Map<String, Object> map) {
		return ADD;
	}

	// 準備更新
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOne(@PathVariable("id") Integer id, Map<String, Object> map) {
		return UPDATE;
	}

	// 新增
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(CommodityVO vo, Map<String, Object> map) {
		vo.setIsStored(true);
		commodityService.create(vo);
		List<CommodityDisplay> list = new ArrayList<>();
		list.add(commodityService.getCommodityDisplayVO(vo));
		map.put("commodityList", list);
		return LIST;
	}

	// 修改
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, @ModelAttribute(value = "commodity") CommodityVO vo,
			Map<String, Object> map, @RequestParam Map<String, Object> map2) {
		commodityService.update(vo);
		List<CommodityDisplay> list = new ArrayList<>();
		list.add(commodityService.getCommodityDisplayVO(vo));
		map.put("commodityList", list);
		return LIST;
	}

	// 刪除多筆
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete(@RequestBody String[] commodityIds) {
		try {
			Integer[] ids = new Integer[commodityIds.length];
			for (int i = 0; i < commodityIds.length; i++) {
				ids[i] = Integer.valueOf(commodityIds[i]);
			}
			commodityService.delete(ids);
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除失敗").toString();
		}
	}

	// 複製
	@RequestMapping(value = "/clone", method = RequestMethod.POST)
	public String clone(@RequestParam(value = "commodityIds", required = true) Integer id) {
		commodityService.create(commodityService.getOne(id));
		return REDIRECT_LIST;
	}

}
