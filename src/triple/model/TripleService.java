package triple.model;

import java.util.List;
import java.util.Set;

import commodity.model.CommodityService;
import commodity.model.CommodityVO;
import commodity.model.CommodityWithPicCountVO;
import purchaseCase.model.PurchaseCaseVO;
import sellCase.model.SellCaseService;
import sellCase.model.SellCaseVO;
import sellCase.model.SellCaseWithBenefitVO;

public class TripleService {

	private final CommodityService cs = new CommodityService();
	private SellCaseService scs = new SellCaseService();

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
