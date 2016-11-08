package com.jersey.sellCase.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.purchaseCase.model.PurchaseCaseDAO;
import com.jersey.purchaseCase.model.PurchaseCaseVO;
import com.jersey.tools.Tools;

@Service
public class SellCaseService {
	
	@Autowired
	private PurchaseCaseDAO purchaseCaseDAO;
	@Autowired
	private SellCaseDAO sellCaseDAO;

	public List<SellCaseWithBenefitVO> getAll(Integer pageSize, Integer page) {
		return getSellCaseWithBenefitVOList(sellCaseDAO.getAll(pageSize, page));
	}

	public SellCaseVO getOne(Integer id) {
		return sellCaseDAO.getOne(id);
	}

	public SellCaseVO create(SellCaseVO vo) {
		vo.setUncollected(vo.getIncome() - vo.getCollected());
		
		if (vo.getIsShipping()) {
			vo.setShippingTime(new Date());
		}
		
		if ((vo.getUncollected() == 0) && (vo.getIsChecked())) {
			vo.setCloseTime(new Date());
		}
		
		return this.sellCaseDAO.create(vo);
	}

	public SellCaseVO update(SellCaseVO vo) {
		vo.setUncollected(vo.getIncome() - vo.getCollected());
		
		if (vo.getIsShipping()) {
			vo.setShippingTime(new Date());
		}
		
		if ((vo.getUncollected() == 0) && (vo.getIsChecked())) {
			vo.setCloseTime(new Date());
		}
		return this.sellCaseDAO.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.sellCaseDAO.delete(ids);
	}

	public Set<PurchaseCaseVO> getPurchaseCasesBySellCaseId(Integer id) {
		SellCaseVO sellCaseVO = this.sellCaseDAO.getOne(id);
		if (sellCaseVO != null)
			return sellCaseVO.getPurchaseCases();
		return new HashSet<PurchaseCaseVO>();
	}

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		return purchaseCaseDAO.getPurchaseCasesBySellCaseIdIsNull();
	}

	public void addSellCaseIdToPurchaseCases(Integer sellCaseId, Integer[] purchaseCaseIds) {
		purchaseCaseDAO.updateSellCaseId(sellCaseId, purchaseCaseIds);
	}

	public void deleteSellCaseIdFromPurchaseCases(Integer[] purchaseCaseIds) {
		purchaseCaseDAO.deleteSellCaseId(purchaseCaseIds);
	}

	public List<SellCaseWithBenefitVO> getUncollectedNotZero() {
		return getSellCaseWithBenefitVOList(sellCaseDAO.getUncollectedNotZero());
	}

	public List<SellCaseWithBenefitVO> getIsClosed() {
		return getSellCaseWithBenefitVOList(sellCaseDAO.getIsClosed());
	}

	public List<SellCaseWithBenefitVO> getNotClosed() {
		return getSellCaseWithBenefitVOList(sellCaseDAO.getNotClosed());
	}

	public List<SellCaseWithBenefitVO> getBetweenCloseTime(Date start, Date end) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return getSellCaseWithBenefitVOList(sellCaseDAO.getBetweenCloseTime(start, end));
//		List<SellCaseVO> list = new ArrayList<SellCaseVO>();
//
//		for (Object[] object : sellCaseList) {
//			SellCaseVO sellCaseVO = new SellCaseVO();
//			sellCaseVO.setSellCaseId((Integer) object[0]);
//			sellCaseVO.setAddressee((String) object[1]);
//			sellCaseVO.setPhone((String) object[2]);
//			sellCaseVO.setAddress((String) object[3]);
//			sellCaseVO.setDescription((String) object[4]);
//			sellCaseVO.setTrackingNumber((String) object[5]);
//			sellCaseVO.setTransportMethod((String) object[6]);
//			if (((Byte) object[7]).byteValue() == 1)
//				sellCaseVO.setIsShipping(Boolean.valueOf(true));
//			else
//				sellCaseVO.setIsShipping(Boolean.valueOf(false));
//			sellCaseVO.setIncome((Integer) object[8]);
//			sellCaseVO.setTransportCost((Integer) object[9]);
//			sellCaseVO.setCollected((Integer) object[10]);
//			sellCaseVO.setUncollected((Integer) object[11]);
//			sellCaseVO.setShippingTime((String) object[12]);
//			sellCaseVO.setCloseTime((String) object[13]);
//			if (((Byte) object[14]).byteValue() == 1)
//				sellCaseVO.setIsChecked(Boolean.valueOf(true));
//			else
//				sellCaseVO.setIsChecked(Boolean.valueOf(false));
//			list.add(sellCaseVO);
//		}		
	}
	
	public SellCaseWithBenefitVO getSellCaseWithBenefitVo (SellCaseVO sellCaseVO) {
		SellCaseWithBenefitVO sellCaseWithBenefitVo = null;
		if (sellCaseVO != null) {
			sellCaseWithBenefitVo = new SellCaseWithBenefitVO();
			Tools.copyBeanProperties(sellCaseVO, sellCaseWithBenefitVo);
			
			Integer costs = 0;
			Integer agentCosts = 0;
			Set<PurchaseCaseVO> purchaseCases = sellCaseVO.getPurchaseCases();
			if (purchaseCases!=null) {
				for (PurchaseCaseVO purchaseCaseVO : purchaseCases) {
					costs = costs + purchaseCaseVO.getCost();
					agentCosts = agentCosts + purchaseCaseVO.getAgentCost();
				}
			}
			sellCaseWithBenefitVo.setBenefit(sellCaseVO.getCollected() - sellCaseVO.getTransportCost() - costs - agentCosts);
			sellCaseWithBenefitVo.setEstimateBenefit(sellCaseVO.getIncome() - sellCaseVO.getTransportCost() - costs - agentCosts);
			sellCaseWithBenefitVo.setCosts(costs);
			sellCaseWithBenefitVo.setAgentCosts(agentCosts);
		}
		return sellCaseWithBenefitVo;
	}
	
	public List<SellCaseWithBenefitVO> getSellCaseWithBenefitVOList (Collection<SellCaseVO> sellCaseList) {
		List<SellCaseWithBenefitVO> sellCaseWithBenefitList = new ArrayList<>();
		SellCaseWithBenefitVO sellCaseWithBenefitVo;
		for (SellCaseVO sellCaseVO : sellCaseList) {
			sellCaseWithBenefitVo = getSellCaseWithBenefitVo(sellCaseVO);
			sellCaseWithBenefitList.add(sellCaseWithBenefitVo);
		}
		return sellCaseWithBenefitList;
	}
 
	
	public Integer getTotalBenefit (List<SellCaseWithBenefitVO> list) {
		Integer totalBenefit = 0;
		for (SellCaseWithBenefitVO sellCaseWithBenefitVO : list) {
			totalBenefit+=sellCaseWithBenefitVO.getBenefit();
		}
		return totalBenefit;
	}

	public Long getTotalCount() {
		return sellCaseDAO.getTotalCount();
	}
	
	
	
	
	
	
	
	
	
}
