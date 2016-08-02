package com.jersey.triple.model;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.commodity.model.CommodityWithPicCountVO;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.sellCase.model.SellCaseService;
import com.jersey.sellCase.model.SellCaseVO;
import com.jersey.sellCase.model.SellCaseWithBenefitVO;

@Service
public class TripleService {

	@Autowired
	private CommodityService cs;
	
	@Autowired
	private SellCaseService scs;

	public void generateTriple(CommodityVO commodityVO, List<CommodityWithPicCountVO> commodityVOList,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		commodityVOList.add(cs.getCommodityWithPicCountVO(commodityVO));
		purchaseCaseVOList.add(commodityVO.getPurchaseCaseVO());
		if (commodityVO.getPurchaseCaseVO() != null && commodityVO.getPurchaseCaseVO().getSellCaseVO() != null) {
			sellCaseWithBenefitVOList
					.add(scs.getSellCaseWithBenefitVo(commodityVO.getPurchaseCaseVO().getSellCaseVO()));
		}
	}

	public void generateTriple(PurchaseCaseVO purchaseCaseVO, Set<CommodityWithPicCountVO> commodityVOSet,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		Set<CommodityVO> commodityVOs = purchaseCaseVO.getCommoditys();
		commodityVOSet.addAll(cs.getCommodityWithPicCountList(commodityVOs));
		purchaseCaseVOList.add(purchaseCaseVO);
		sellCaseWithBenefitVOList.add(scs.getSellCaseWithBenefitVo(purchaseCaseVO.getSellCaseVO()));
	}

	public void generateTriple(SellCaseVO sellCaseVO, Set<CommodityWithPicCountVO> commodityVOSet,
			Set<PurchaseCaseVO> purchaseCaseVOSet, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		SellCaseWithBenefitVO sellCaseWithBenefitVO = scs.getSellCaseWithBenefitVo(sellCaseVO);
		sellCaseWithBenefitVOList.add(sellCaseWithBenefitVO);
		purchaseCaseVOSet.addAll(sellCaseWithBenefitVO.getPurchaseCases());
		for (PurchaseCaseVO purchaseCaseVO : purchaseCaseVOSet) {
			Set<CommodityVO> commodityVOs = purchaseCaseVO.getCommoditys();
			commodityVOSet.addAll(cs.getCommodityWithPicCountList(commodityVOs));
		}

	}

}
