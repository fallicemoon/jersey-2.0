package sellCase.model;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import tools.AbstractDAO;

public class SellCaseDAO extends AbstractDAO<SellCaseVO> {

	public SellCaseDAO() {
		super(SellCaseVO.class, "sellCaseId");
	}

	// public List<SellCaseVO> getAll() {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// Query query = session.createQuery("from SellCaseVO");
	// List<SellCaseVO> list = query.list();
	// session.getTransaction().commit();
	//
	// return list;
	// }
	//
	// public SellCaseVO getOne(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// session.getTransaction().begin();
	// SellCaseVO vo = (SellCaseVO) session.get(SellCaseVO.class, id);
	// session.getTransaction().commit();
	//
	// return vo;
	// }

	public List<SellCaseVO> getUncollectedNotZero() {
		return getHelper(Restrictions.ne("uncollected", 0));
		// session =
		// HibernateSessionFactory.getSessionFactory().getCurrentSession();
		// session.getTransaction().begin();
		// Query query = session.createQuery("from SellCaseVO where uncollected
		// is not 0");
		// List<SellCaseVO> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
	}

	public List<SellCaseVO> getBetweenCloseTime(Date start, Date end) {

		return getHelper(Restrictions.between("closeTime", start, end));
		// session =
		// HibernateSessionFactory.getSessionFactory().getCurrentSession();
		// session.getTransaction().begin();
		// Query query = session.createSQLQuery(
		// "select * from sell_case where close_time between CAST(:start AS
		// DATETIME) AND CAST(:end AS DATETIME)");
		// query.setParameter("start", start);
		// query.setParameter("end", end);
		//
		// List<Object[]> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
	}

	public List<SellCaseVO> getNotClosed() {
		return getHelper(Restrictions.or(Restrictions.ne("uncollected", 0), Restrictions.eq("isChecked", false)));
		// session =
		// HibernateSessionFactory.getSessionFactory().getCurrentSession();
		// try {
		// session.beginTransaction();
		// Query query = session.createQuery("from SellCaseVO where
		// uncollected!=0 or isChecked=false");
		// List<SellCaseVO> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
		// } catch (HibernateException e) {
		// e.printStackTrace();
		// return new ArrayList<SellCaseVO>();
		// }

	}

	public List<SellCaseVO> getIsClosed() {

		return getHelper(Restrictions.and(Restrictions.eq("uncollected", 0), Restrictions.eq("isChecked", true)));
		// session =
		// HibernateSessionFactory.getSessionFactory().getCurrentSession();
		// try {
		// session.beginTransaction();
		// Query query = session.createQuery("from SellCaseVO where
		// uncollected=0 and isChecked=true");
		// List<SellCaseVO> list = query.list();
		// session.getTransaction().commit();
		//
		// return list;
		// } catch (HibernateException e) {
		// e.printStackTrace();
		//
		// return new ArrayList<SellCaseVO>();
		// }

	}

	// public Integer create(SellCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// Integer id = (Integer) session.save(vo);
	// session.getTransaction().commit();
	//
	// return id;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	//
	// }
	// return null;
	// }
	//
	// public boolean update(SellCaseVO vo) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// session.update(vo);
	// session.getTransaction().commit();
	//
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	//
	// }
	// return false;
	// }
	//
	// public boolean delete(Integer id) {
	// session =
	// HibernateSessionFactory.getSessionFactory().getCurrentSession();
	// try {
	// session.beginTransaction();
	// SellCaseVO vo = new SellCaseVO();
	// vo.setSellCaseId(id);
	// session.delete(vo);
	// session.getTransaction().commit();
	//
	// return true;
	// } catch (HibernateException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// public boolean delete(Integer[] ids) {
	// for (Integer id : ids) {
	// delete(id);
	// }
	// return true;
	// }

}
