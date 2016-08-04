package com.jersey.purchaseCase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jersey.purchaseCase.model.PurchaseCaseService;
import com.jersey.purchaseCase.model.PurchaseCaseVO;

@Controller
@RequestMapping(value="/purchaseCase")
public class PurchaseCaseController {
	private static final String LIST = "purchaseCase/list";
	private static final String ADD = "purchaseCase/add";
	private static final String UPDATE = "purchaseCase/update";
	private static final String ADD_COMMODITY = "purchaseCase/addCommodity";
	private static final String REDIRECT_ADD_COMMODITY = "redirect:getCommodityList/{id}";
	
	@Autowired
	private PurchaseCaseService purchaseCaseService;
	
	//取得全部
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public String getAll(Map<String, Object> map){
		map.put("purchaseCaseList", purchaseCaseService.getAll());
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
		map.put("purchaseCase", purchaseCaseService.getOne(id));
		return UPDATE;
	}
	
	//新增
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create (PurchaseCaseVO vo, Map<String, Object> map) {
		purchaseCaseService.create(vo);
		List<PurchaseCaseVO> list = new ArrayList<>();
		list.add(vo);
		map.put("purchaseCaseList", list);
		return LIST;
	}
	
	//修改
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update (@PathVariable("id") Integer id, PurchaseCaseVO vo, Map<String, Object> map) {
		purchaseCaseService.update(vo);
		List<PurchaseCaseVO> list = new ArrayList<>();
		list.add(vo);
		map.put("purchaseCaseList", list);
		return LIST;
	}
	
	//刪除多筆
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String delete (Map<String, Object> map, @RequestParam String[] purchaseCaseIds) {
		Integer[] ids = new Integer[purchaseCaseIds.length];
		for (int i = 0; i < purchaseCaseIds.length; i++) {
			ids[i] = Integer.valueOf(purchaseCaseIds[i]);
		}
		purchaseCaseService.delete(ids);
		return LIST;
	}
	
	//取得可以新增到進貨的商品
	@RequestMapping(value="/getCommodityList/{id}", method=RequestMethod.GET)
	public String getCommodityList (@PathVariable("id") Integer purchaseCaseId, Map<String, Object> map) {
		// 取得已經在進貨單中的商品清單
		map.put("commodityListInPurchaseCase", purchaseCaseService.getCommoditysByPurchaseCaseId(purchaseCaseId));
		// 取得可以新增在進貨單中的商品清單
		map.put("commodityListNotInPurchaseCase", purchaseCaseService.getCommoditysByPurchaseCaseIdIsNull());
		return ADD_COMMODITY;
	}
	
	//新增商品到進貨
	@RequestMapping(value = "/addCommodity", method = RequestMethod.PUT)
	public String addCommodity(@RequestParam(value = "purchaseCaseId", required = true) Integer purchaseCaseId,
			@RequestParam(value = "commodityIds", required = true) String[] commodityIds) {
		Integer[] ids = new Integer[commodityIds.length];
		for (int i = 0; i < commodityIds.length; i++) {
			ids[i] = Integer.valueOf(commodityIds[i]);
		}
		purchaseCaseService.addPurchaseCaseIdToCommoditys(purchaseCaseId, ids);
		return REDIRECT_ADD_COMMODITY.replace("{id}", purchaseCaseId.toString());
	}
	
	//移除進貨裡面的商品
	@RequestMapping(value="/removeCommodity", method=RequestMethod.PUT)
	public String removeCommodity(@RequestParam(value = "purchaseCaseId", required = true) Integer purchaseCaseId,
			@RequestParam(value = "commodityIds", required = true) String[] commodityIds) {
		Integer[] ids = new Integer[commodityIds.length];
		for (int i = 0; i < commodityIds.length; i++) {
			ids[i] = Integer.valueOf(commodityIds[i]);
		}
		purchaseCaseService.deletePurchasCaseIdFromCommoditys(ids);
		return REDIRECT_ADD_COMMODITY.replace("{id}", purchaseCaseId.toString());
	}
	
	
	
	
	
	
	
	
}
