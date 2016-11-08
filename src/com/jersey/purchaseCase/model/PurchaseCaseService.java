package com.jersey.purchaseCase.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jersey.commodity.model.CommodityDAO;
import com.jersey.commodity.model.CommodityVO;

@Service
public class PurchaseCaseService {
	
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private PurchaseCaseDAO purchaseCaseDAO;

	public List<PurchaseCaseVO> getAll(Integer pageSize, Integer page) {
		return purchaseCaseDAO.getAll(pageSize, page);
	}

	public List<PurchaseCaseVO> getAllOfNotComplete() {
		return this.purchaseCaseDAO.getAllOfNotProgress("進貨完成");
	}

	public PurchaseCaseVO getOne(Integer id) {
		return this.purchaseCaseDAO.getOne(id);
	}

	public PurchaseCaseVO create(PurchaseCaseVO vo) {
		return this.purchaseCaseDAO.create(vo);
	}

	public PurchaseCaseVO update(PurchaseCaseVO vo) {
		return this.purchaseCaseDAO.update(vo);
	}

	public boolean delete(Integer[] ids) {
		return this.purchaseCaseDAO.delete(ids);
	}

	public Set<CommodityVO> getCommoditysByPurchaseCaseId(Integer id) {
		PurchaseCaseVO vo = this.purchaseCaseDAO.getOne(id);
		if (vo != null)
			return vo.getCommoditys();
		return new HashSet<>();
	}

	public List<CommodityVO> getCommoditysByPurchaseCaseIdIsNull() {
		return commodityDAO.getByPurchaseCaseIdIsNull();
	}

	public void addPurchaseCaseIdToCommoditys(Integer purchaseCaseId, Integer[] commodityIds) {
		PurchaseCaseVO purchaseCaseVO = getOne(purchaseCaseId);
		commodityDAO.updatePurchaseCaseId(purchaseCaseVO, commodityIds);
	}

	public void deletePurchasCaseIdFromCommoditys(Integer[] commodityIds) {
		commodityDAO.deletePurchaseCaseId(commodityIds);
	}

	public Long getTotalCount() {
		return purchaseCaseDAO.getTotalCount();
	}
}

