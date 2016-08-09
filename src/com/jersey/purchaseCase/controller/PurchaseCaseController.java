package com.jersey.purchaseCase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jersey.purchaseCase.model.PurchaseCaseService;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.tools.Tools;

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
	
	//for update purchaseCase用
	@ModelAttribute
	public void getPurchaseCase (Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if(keySet.contains("id")){
			String storeId = pathVariableMap.get("id");
			map.put("purchaseCase", purchaseCaseService.getOne(Integer.valueOf(storeId)));
		}
	}
	
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
	public String update (@PathVariable("id") Integer id, @ModelAttribute("purchaseCase") PurchaseCaseVO vo, Map<String, Object> map) {
		purchaseCaseService.update(vo);
		List<PurchaseCaseVO> list = new ArrayList<>();
		list.add(vo);
		map.put("purchaseCaseList", list);
		return LIST;
	}
	
	//刪除多筆
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String delete (@RequestBody String json) {
		try {
			JSONArray jsonArray = new JSONArray(json);
			List<Object> purchaseCaseIds = jsonArray.toList();
			Integer[] ids = new Integer[purchaseCaseIds.size()];
			for (int i = 0; i < purchaseCaseIds.size(); i++) {
				ids[i] = Integer.valueOf(purchaseCaseIds.get(i).toString());
			}
			purchaseCaseService.delete(ids);
			return Tools.getSuccessJson().toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Tools.getFailJson().toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return Tools.getFailJson().toString();			
		}
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
