package com.jersey.systemParam.model;

import java.util.Date;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.PrimaryKey;

@Repository
public class SystemParamDAO extends AbstractDAO<SystemParamVO> {
	
	@Autowired
	private HibernateTools hibernateTools;

	public SystemParamDAO(Class<SystemParamVO> type, String pk) {
		super(SystemParamVO.class, "name");
	}
	
	/**
	 * 跟DB要號並更新, 所以要lock row
	 * Exception拋到最外面由controller處理(顯示新增失敗訊息)
	 * @param primaryKey
	 * @param poolSize
	 * @return
	 */
	public SystemParamVO getPrimaryKeyValueAndUpdate (PrimaryKey primaryKey, Integer poolSize) {
		Session session = hibernateTools.getSession();
		session.beginTransaction();
		try {
			//TODO 要做壓力測試
			LockOptions lockOptions = LockOptions.UPGRADE;
			lockOptions.setTimeOut(3000);
			SystemParamVO vo = (SystemParamVO) session.get(SystemParamVO.class, primaryKey, lockOptions);
			vo.setValue(vo.getValue() + poolSize);
			vo.setLastModifyTime(new Date());
			session.update(vo);
			session.getTransaction().commit();
			return vo;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 流水號滿了, reset
	 * Exception拋到最外面由controller處理(顯示新增失敗訊息)
	 * @param primaryKey
	 */
	public void resetPrimaryKey (PrimaryKey primaryKey) {
		Session session = hibernateTools.getSession();
		session.beginTransaction();
		try {
			SystemParamVO vo = (SystemParamVO) session.get(SystemParamVO.class, primaryKey);
			vo.setValue("0");
			vo.setLastModifyTime(new Date());
			session.update(vo);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}

}
