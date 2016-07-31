package purchaseCase.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sellCase.model.SellCaseVO;
import tools.AbstractDAO;
import tools.HibernateSessionFactory;

public class PurchaseCaseDAO extends AbstractDAO<PurchaseCaseVO> {
	
//	private static final String GET_ALL = "select p.purchaseCaseId as purchaseCaseId, p.sellCaseVO as sellCaseVO, p.store as store,"
//			+ "p.progress as progress, p.shippingCompany as shippingCompany, p.trackingNumber as trackingNumber,"
//			+ "p.trackingNumberLink as trackingNumberLink, p.agent as agent, p.agentTrackingNumber as agentTrackingNumber,"
//			+ "p.agentTrackingNumberLink as agentTrackingNumberLink, p.isAbroad as isAbroad, p.cost as cost,"
//			+ "p.agentCost as agentCost, p.description as description, p.time as time, a.name as storeName, b.name as shippingCompanyName "
//			+ "from PurchaseCaseVO as p left join p.store as a left join p.shippingCompany as b";

	public PurchaseCaseDAO() {
		super(PurchaseCaseVO.class, "purchaseCaseId");
	}

	// public List<PurchaseCaseVO> getAll() {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// Query query = session.createQuery("from PurchaseCaseVO");
	// List<PurchaseCaseVO> list = query.list();
	// session.getTransaction().commit();
	//
	// return list;
	// }


//	public List<PurchaseCaseWithStoreNameVO> getAllWithStoreName() {
//		List<PurchaseCaseWithStoreNameVO> list;
//		Session session = HibernateSessionFactory.getSession();
//		session.beginTransaction();
//		try {
//			Query query = session.createQuery(GET_ALL);
//			query.setResultTransformer(Transformers.aliasToBean(PurchaseCaseWithStoreNameVO.class));
//			list = query.list();
//			session.getTransaction().commit();
//		} catch (Exception e) {
//			session.getTransaction().rollback();
//			list = new ArrayList<>();
//			e.printStackTrace();
//		}
//		return list;
//	}

	public List<PurchaseCaseVO> getAllOfNotProgress(String progress) {
		return getHelper(Restrictions.ne("progress", progress));
		// session = HibernateSessionFactory.getSession();
		// session.getTransaction().begin();
		// Query query = session.createQuery("from PurchaseCaseVO where progress
		// is not :progress");
		// query.setParameter("progress", progress);
		// List<PurchaseCaseVO> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
	}

	// public PurchaseCaseVO getOne(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// PurchaseCaseVO vo = (PurchaseCaseVO) session.get(PurchaseCaseVO.class,
	// id);
	// session.getTransaction().commit();
	//
	// return vo;
	// }

	// public Integer create(PurchaseCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// Integer id = (Integer) session.save(vo);
	// session.getTransaction().commit();
	// return id;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	// public boolean update(PurchaseCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// session.update(vo);
	// session.getTransaction().commit();
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }

	// public boolean delete(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// PurchaseCaseVO vo = new PurchaseCaseVO();
	// vo.setPurchaseCaseId(id);
	// session.delete(vo);
	// session.getTransaction().commit();
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }

	// public boolean delete(Integer[] ids) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	//
	// for (Integer id : ids) {
	// try {
	// session.beginTransaction();
	// PurchaseCaseVO vo = new PurchaseCaseVO();
	// vo.setPurchaseCaseId(id);
	// session.delete(vo);
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
	//
	// session.getTransaction().commit();
	//
	// return true;
	// }

	public List<PurchaseCaseVO> getPurchaseCasesBySellCaseIdIsNull() {
		return getHelper(Restrictions.isNull("sellCaseVO"));
	}

	public boolean updateSellCaseId(Integer sellCaseId, Integer[] purchaseCaseIds) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			SellCaseVO sellCaseVO = new SellCaseVO();
			sellCaseVO.setSellCaseId(sellCaseId);

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(sellCaseVO);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSellCaseId(Integer[] purchaseCaseIds) {
		Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			for (Integer purchaseCaseId : purchaseCaseIds) {
				PurchaseCaseVO purchaseCaseVO = (PurchaseCaseVO) session.load(PurchaseCaseVO.class, purchaseCaseId);
				purchaseCaseVO.setSellCaseVO(null);
				session.update(purchaseCaseVO);
			}

			// 避免batch太大, out of memory
			int count = 0;
			if (++count % 20 == 0) {
				session.flush();
				session.clear();
			}

			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	//先刪掉商品再刪進貨
	@Override
	public boolean delete(Integer[] ids) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from CommodityVO vo where vo.purchaseCaseVO.purchaseCaseId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.createQuery("delete from PurchaseCaseVO vo where vo.purchaseCaseId in (:ids)").setParameterList("ids", ids).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
}
