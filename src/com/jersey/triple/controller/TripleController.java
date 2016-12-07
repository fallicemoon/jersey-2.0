package com.jersey.triple.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jersey.commodity.model.CommodityDisplayVO;
import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.purchaseCase.model.PurchaseCaseService;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.sellCase.model.SellCaseService;
import com.jersey.sellCase.model.SellCaseVO;
import com.jersey.sellCase.model.SellCaseWithBenefitVO;
import com.jersey.triple.model.TripleService;

@Controller
@RequestMapping("/triple")
public class TripleController {
	
	private final static String LIST_ONE = "triple/listOne";
	
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private PurchaseCaseService purchaseCaseService;
	@Autowired
	private SellCaseService SellCaseService;
	@Autowired
	private TripleService tripleService;
		
	@RequestMapping(value = "/commodity/{id}", method = RequestMethod.GET)
	public String commodity (@PathVariable("id") String commodityId, Map<String, Object> map) {
		List<CommodityDisplayVO> commodityList = new ArrayList<>();
		List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
		List<SellCaseWithBenefitVO> sellCaseWithBenefitList = new ArrayList<>();

		CommodityVO commodityVO = commodityService.getOne(commodityId);
		tripleService.generateTriple(commodityVO, commodityList, purchaseCaseList, sellCaseWithBenefitList);

		map.put("title", "商品:" + commodityVO.getId() + "/" + commodityVO.getItemName());
		map.put("commodityList", commodityList);
		map.put("purchaseCaseList", purchaseCaseList);
		map.put("sellCaseList", sellCaseWithBenefitList);
		return LIST_ONE;
	}
	
	@RequestMapping(value = "/purchaseCase/{id}", method = RequestMethod.GET)
	public String purchaseCase (@PathVariable("id") String purchaseCaseId, Map<String, Object> map) {
		Set<CommodityDisplayVO> commoditys = new LinkedHashSet<>();
		List<PurchaseCaseVO> purchaseCaseList = new ArrayList<>();
		List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();

		PurchaseCaseVO purchaseCaseVO = purchaseCaseService.getOne(purchaseCaseId);
		tripleService.generateTriple(purchaseCaseVO, commoditys, purchaseCaseList, sellCaseList);

		map.put("title",
				"進貨:" + purchaseCaseVO.getId() + "/" + purchaseCaseVO.getStore().getName());
		map.put("commodityList", commoditys);
		map.put("purchaseCaseList", purchaseCaseList);
		map.put("sellCaseList", sellCaseList);
		return LIST_ONE;
	}
	
	@RequestMapping(value = "/sellCase/{id}", method = RequestMethod.GET)
	public String sellCase (@PathVariable("id") String sellCaseId, Map<String, Object> map) {
		Set<CommodityDisplayVO> commoditys = new LinkedHashSet<>();
		Set<PurchaseCaseVO> purchaseCases = new LinkedHashSet<>();
		List<SellCaseWithBenefitVO> sellCaseList = new ArrayList<>();
		SellCaseVO sellCaseVO = SellCaseService.getOne(sellCaseId);

		tripleService.generateTriple(sellCaseVO, commoditys, purchaseCases, sellCaseList);

		map.put("title", "出貨:" + sellCaseVO.getId() + "/" + sellCaseVO.getAddressee());
		map.put("commodityList", commoditys);
		map.put("purchaseCaseList", purchaseCases);
		map.put("sellCaseList", sellCaseList);
		return LIST_ONE;
	}
	

}
