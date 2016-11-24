package com.jersey.sellCase.controller;

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

import com.jersey.sellCase.model.SellCaseService;
import com.jersey.sellCase.model.SellCaseVO;
import com.jersey.sellCase.model.SellCaseWithBenefitVO;
import com.jersey.tools.Tools;
import com.jersey.userConfig.model.UserSession;

@Controller
@RequestMapping(value="/sellCase")
public class SellCaseController {
	private static final String LIST = "sellCase/list";
	private static final String ADD = "sellCase/add";
	private static final String UPDATE = "sellCase/update";
	private static final String ADD_PURCHASE_CASE = "sellCase/addPurchaseCase";
	private static final String REDIRECT_ADD_PURCHASE_CASE = "redirect:getPurchaseCaseList/{id}";
	private static final String REDIRECT_LIST = "redirect:getAll";
	
	@Autowired
	private SellCaseService sellCaseService;
	@Autowired
	private UserSession userSession;
	
	//for update sellCase用
	@ModelAttribute
	public void getSellCase (Map<String, Object> map, @PathVariable Map<String, String> pathVariableMap) {
		Set<String> keySet = pathVariableMap.keySet();
		if(keySet.contains("id")){
			String sellCaseId = pathVariableMap.get("id");
			map.put("sellCase", sellCaseService.getOne(Integer.valueOf(sellCaseId)));
		}
	}
	
	//取得全部
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(Map<String, Object> map,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
		map.put("sellCaseList", sellCaseService.getAll(userSession.getUserConfigVO().getSellCasePageSize(), page));
		Long count = sellCaseService.getTotalCount()/userSession.getUserConfigVO().getSellCasePageSize();
		if (sellCaseService.getTotalCount()%userSession.getUserConfigVO().getSellCasePageSize()!=0) {
			count++;
		}
		map.put("pages", count);
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
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String create (SellCaseVO vo, Map<String, Object> map) {
		sellCaseService.create(vo);
//		List<SellCaseWithBenefitVO> list = new ArrayList<>();
//		list.add(sellCaseService.getSellCaseWithBenefitVo(vo));
//		map.put("sellCaseList", list);
		return REDIRECT_LIST;
	}
	
	//修改
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public String update (@PathVariable("id") Integer id, @ModelAttribute("sellCase") SellCaseVO vo, Map<String, Object> map) {
		sellCaseService.update(vo);
//		List<SellCaseWithBenefitVO> list = new ArrayList<>();
//		list.add(sellCaseService.getSellCaseWithBenefitVo(vo));
//		map.put("sellCaseList", list);
		return REDIRECT_LIST;
	}
	
	// 刪除多筆
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces="application/json;charset=UTF-8")
	public String delete(@RequestBody String[] sellCaseIds) {
		try {
			Integer[] ids = new Integer[sellCaseIds.length];
			for (int i = 0; i < sellCaseIds.length; i++) {
				ids[i] = Integer.valueOf(sellCaseIds[i]);
			}
			if (!sellCaseService.delete(ids)) {
				throw new Exception();
			}
			return Tools.getSuccessJson().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Tools.getFailJson("刪除失敗").toString();
		}
	}
	
	//取得可以新增到出貨的進貨
	@RequestMapping(value="/getPurchaseCaseList/{id}", method=RequestMethod.GET)
	public String getPurchaseCaseList (@PathVariable("id") Integer sellCaseId, Map<String, Object> map) {
		map.put("sellCaseId", sellCaseId);
		// 取得已經在進貨單中的商品清單
		map.put("purchaseCaseListInSellCase", sellCaseService.getPurchaseCasesBySellCaseId(sellCaseId));
		// 取得可以新增在進貨單中的商品清單
		map.put("purchaseCaseListNotInSellCase", sellCaseService.getPurchaseCasesBySellCaseIdIsNull());
		return ADD_PURCHASE_CASE;
	}
	
	//新增進貨到出貨
	@RequestMapping(value = "/addPurchaseCase", method = RequestMethod.PUT)
	public String addPurchaseCase(@RequestParam(value = "sellCaseId", required = true) Integer sellCaseId,
			@RequestParam(value = "purchaseCaseIds", required = true) String[] purchaseCaseIds) {
		Integer[] ids = new Integer[purchaseCaseIds.length];
		for (int i = 0; i < purchaseCaseIds.length; i++) {
			ids[i] = Integer.valueOf(purchaseCaseIds[i]);
		}
		sellCaseService.addSellCaseIdToPurchaseCases(sellCaseId, ids);
		return REDIRECT_ADD_PURCHASE_CASE.replace("{id}", sellCaseId.toString());
	}
	
	//移除出貨裡面的進貨
	@RequestMapping(value="/removePurchaseCase", method=RequestMethod.PUT)
	public String removeCommodity(@RequestParam(value = "sellCaseId", required = true) Integer sellCaseId,
			@RequestParam(value = "purchaseCaseIds", required = true) String[] purchaseCaseIds) {
		Integer[] ids = new Integer[purchaseCaseIds.length];
		for (int i = 0; i < purchaseCaseIds.length; i++) {
			ids[i] = Integer.valueOf(purchaseCaseIds[i]);
		}
		sellCaseService.deleteSellCaseIdFromPurchaseCases(ids);
		return REDIRECT_ADD_PURCHASE_CASE.replace("{id}", sellCaseId.toString());
	}

}
