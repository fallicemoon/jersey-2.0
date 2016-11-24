package com.jersey.purchaseCase.controller;

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

import com.jersey.purchaseCase.model.PurchaseCaseService;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.UserSession;

@Controller
@RequestMapping(value = "/purchaseCase")
public class PurchaseCaseController {
	private static final String LIST = "purchaseCase/list";
	private static final String ADD = "purchaseCase/add";
	private static final String UPDATE = "purchaseCase/update";
	private static final String ADD_COMMODITY = "purchaseCase/addCommodity";
	private static final String REDIRECT_ADD_COMMODITY = "redirect:getCommodityList/{id}";
	private static final String REDIRECT_LIST = "redirect:getAll";

	@Autowired
	private PurchaseCaseService purchaseCaseService;
	@Autowired
	private UserSession userSession;
	
	// for update purchaseCase用
	@ModelAttribute
	public void getPurchaseCase (Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if (keySet.contains("id")) {
			String purchaseCaseId = pathVariableMap.get("id");
			map.put("purchaseCase", purchaseCaseService.getOne(Integer.valueOf(purchaseCaseId)));
		}
	}

	// 取得全部
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(Map<String, Object> map,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		map.put("purchaseCaseList", purchaseCaseService.getAll(userSession.getUserConfigVO().getPurchaseCasePageSize(), page));
		Long count = purchaseCaseService.getTotalCount()/userSession.getUserConfigVO().getPurchaseCasePageSize();
		if (purchaseCaseService.getTotalCount()%userSession.getUserConfigVO().getPurchaseCasePageSize()!=0) {
			count++;
		}
		map.put("pages", count);
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

	//新增
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String create(PurchaseCaseVO vo, Map<String, Object> map) {
		purchaseCaseService.create(vo);
//		List<PurchaseCaseVO> list = new ArrayList<>();
//		list.add(vo);
//		map.put("purchaseCaseList", list);
		return REDIRECT_LIST;
	}

	// 修改
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable("id") Integer id, @ModelAttribute("purchaseCase") PurchaseCaseVO vo,
			Map<String, Object> map) {
		purchaseCaseService.update(vo);
//		List<PurchaseCaseVO> list = new ArrayList<>();
//		list.add(vo);
//		map.put("purchaseCaseList", list);
		return REDIRECT_LIST;
	}

	// 刪除多筆
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete(@RequestBody String[] purchaseCaseIds) {
		try {
			Integer[] ids = new Integer[purchaseCaseIds.length];
			for (int i = 0; i < purchaseCaseIds.length; i++) {
				ids[i] = Integer.valueOf(purchaseCaseIds[i]);
			}
			if (!purchaseCaseService.delete(ids)) {
				throw new Exception();
			}
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除失敗").toString();
		}
	}

	// 取得可以新增到進貨的商品
	@RequestMapping(value = "/getCommodityList/{id}", method = RequestMethod.GET)
	public String getCommodityList(@PathVariable("id") Integer purchaseCaseId, Map<String, Object> map) {
		map.put("purchaseCaseId", purchaseCaseId);
		// 取得已經在進貨單中的商品清單
		map.put("commodityListInPurchaseCase", purchaseCaseService.getCommoditysByPurchaseCaseId(purchaseCaseId));
		// 取得可以新增在進貨單中的商品清單
		map.put("commodityListNotInPurchaseCase", purchaseCaseService.getCommoditysByPurchaseCaseIdIsNull());
		return ADD_COMMODITY;
	}

	// 新增商品到進貨
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

	// 移除進貨裡面的商品
	@RequestMapping(value = "/removeCommodity", method = RequestMethod.PUT)
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
