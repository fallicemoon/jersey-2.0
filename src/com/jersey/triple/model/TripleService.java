package com.jersey.triple.model;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityService;
import com.jersey.commodity.model.CommodityVO;
import com.jersey.commodity.model.CommodityDisplayVO;
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

	public void generateTriple(CommodityVO commodityVO, List<CommodityDisplayVO> commodityVOList,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		if (commodityVO != null) {
			commodityVOList.add(cs.getCommodityDisplayVO(commodityVO));
			if (commodityVO.getPurchaseCaseVO() != null) {
				purchaseCaseVOList.add(commodityVO.getPurchaseCaseVO());
				if (commodityVO.getPurchaseCaseVO().getSellCaseVO() != null) {
					sellCaseWithBenefitVOList
							.add(scs.getSellCaseWithBenefitVo(commodityVO.getPurchaseCaseVO().getSellCaseVO()));
				}
			}
		}
	}

	public void generateTriple(PurchaseCaseVO purchaseCaseVO, Set<CommodityDisplayVO> commodityVOSet,
			List<PurchaseCaseVO> purchaseCaseVOList, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		if (purchaseCaseVO!=null) {
			purchaseCaseVOList.add(purchaseCaseVO);
			commodityVOSet.addAll(cs.getCommodityDisplayVO(purchaseCaseVO.getCommoditys()));
			if (purchaseCaseVO.getSellCaseVO() != null) {
				sellCaseWithBenefitVOList.add(scs.getSellCaseWithBenefitVo(purchaseCaseVO.getSellCaseVO()));
			}
		}
	}

	public void generateTriple(SellCaseVO sellCaseVO, Set<CommodityDisplayVO> commodityVOSet,
			Set<PurchaseCaseVO> purchaseCaseVOSet, List<SellCaseWithBenefitVO> sellCaseWithBenefitVOList) {
		if (sellCaseVO!=null) {
			sellCaseWithBenefitVOList.add(scs.getSellCaseWithBenefitVo(sellCaseVO));
			purchaseCaseVOSet.addAll(sellCaseVO.getPurchaseCases());
			for (PurchaseCaseVO purchaseCaseVO : sellCaseVO.getPurchaseCases()) {
				Set<CommodityVO> commodityVOs = purchaseCaseVO.getCommoditys();
				commodityVOSet.addAll(cs.getCommodityDisplayVO(commodityVOs));
			}
		}
	}

}
