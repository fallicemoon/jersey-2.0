package com.jersey.sellCase.model;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;

@Repository
public class SellCaseDAO extends AbstractDAO<SellCaseVO> {

	public SellCaseDAO() {
		super(SellCaseVO.class, "sellCaseId");
	}

	public List<SellCaseVO> getUncollectedNotZero() {
		return getHelper(Restrictions.ne("uncollected", 0));
	}

	public List<SellCaseVO> getBetweenCloseTime(Date start, Date end) {
		return getHelper(Restrictions.between("closeTime", start, end));
	}

	public List<SellCaseVO> getNotClosed() {
		return getHelper(Restrictions.or(Restrictions.ne("uncollected", 0), Restrictions.eq("isChecked", false)));
	}

	public List<SellCaseVO> getIsClosed() {
		return getHelper(Restrictions.and(Restrictions.eq("uncollected", 0), Restrictions.eq("isChecked", true)));
	}

}
