package com.jersey.systemParam.model;

import java.util.Date;
import java.util.Optional;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jersey.tools.AbstractDAO;
import com.jersey.tools.HibernateTools;
import com.jersey.tools.JerseyEnum.PrimaryKey;

@Repository
public class SystemParamDAO extends AbstractDAO<SystemParamVO> {
	
	@Autowired
	private HibernateTools hibernateTools;

	public SystemParamDAO() {
		super(SystemParamVO.class);
	}
	
	/**
	 * 因為此方法是在create時才會呼叫，所以不用開新Transaction (開了會nested transaction, Hibernate不支援)
	 * 跟DB要號並更新, 所以要lock row
	 * Exception拋到最外面由controller處理(顯示新增失敗訊息)
	 * @param primaryKey
	 * @param poolSize
	 * @return
	 */
	public SystemParamVO getPrimaryKeyValueAndUpdate (PrimaryKey primaryKey, Integer poolSize) {
		Session session = hibernateTools.getSession();
		Transaction transaction = session.getTransaction();
		
		//因為Hibernate不支援nested transaction, 所以只好這樣寫了QQ......
		boolean flag = transaction.isActive();
		if (!flag) {
			session.beginTransaction();
		}
		
		try {
			//TODO 要做壓力測試
			LockOptions lockOptions = LockOptions.UPGRADE;
			lockOptions.setTimeOut(3000);
			Query<SystemParamVO> query = session.createQuery("from SystemParamVO vo where vo.name=:name", SystemParamVO.class);
			query.setLockOptions(lockOptions);
			query.setParameter("name", primaryKey.toString());
			
			Optional<SystemParamVO> optional = query.uniqueResultOptional();
			SystemParamVO vo = optional.get();
			Integer value = Integer.valueOf(vo.getValue()) + poolSize;
			vo.setValue(value.toString());
			vo.setLastModifyTime(new Date());
			session.update(vo);
			if (!flag) {
				session.getTransaction().commit();
			}
			return vo;
		} catch (Exception e) {
			if (!flag) {
				session.getTransaction().rollback();
			}
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
