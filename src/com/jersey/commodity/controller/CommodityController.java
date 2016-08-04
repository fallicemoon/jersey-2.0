package com.jersey.commodity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.commodity.model.CommodityWithPicCountVO;

@Controller
@RequestMapping(value="/commodity")
public class CommodityController {
	private static final String LIST = "commodity/list";
	private static final String ADD = "commodity/add";
	private static final String UPDATE = "commodity/update";
	private static final String REDIRECT_LIST = "redirect:getAll";
	
	@Autowired
	private CommodityService commodityService;
	
	//取得全部
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public String getAll(Map<String, Object> map){
		map.put("commodityList", commodityService.getAll());
		return LIST;
	}
	
	//準備新增
	@RequestMapping(value="", method=RequestMethod.GET)
	public String get(Map<String, Object> map){
		return ADD;
	}
	
	//準備更新
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String getOne (@PathVariable("id") Integer id, Map<String, Object> map) {
		map.put("commodity", commodityService.getOne(id));
		return UPDATE;
	}
	
	//新增
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create (CommodityVO vo, Map<String, Object> map) {
		vo.setIsStored(true);
		commodityService.create(vo);
		List<CommodityWithPicCountVO> list = new ArrayList<>();
		list.add(commodityService.getCommodityWithPicCountVO(vo));
		map.put("commodityList", list);
		return LIST;
	}
	
	//修改
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update (@PathVariable("id") Integer id, CommodityVO vo, Map<String, Object> map) {
		commodityService.update(vo);
		List<CommodityWithPicCountVO> list = new ArrayList<>();
		list.add(commodityService.getCommodityWithPicCountVO(vo));
		map.put("commodityList", list);
		return LIST;
	}
	
	//刪除多筆
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String delete (Map<String, Object> map, @RequestParam String[] commodityIds) {
		Integer[] ids = new Integer[commodityIds.length];
		for (int i = 0; i < commodityIds.length; i++) {
			ids[i] = Integer.valueOf(commodityIds[i]);
		}
		commodityService.delete(ids);
		return LIST;
	}
	
	//複製
	@RequestMapping(value="/clone", method=RequestMethod.POST)
	public String clone (@RequestParam(value="commodityId", required=true) Integer id) {
		commodityService.create(commodityService.getOne(id));
		return REDIRECT_LIST;
	}
	
	

}
