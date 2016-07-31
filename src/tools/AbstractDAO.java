package tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.transform.Transformers;

public abstract class AbstractDAO<E extends AbstractVo> implements DAOInterface<E> {

	private Class<E> voType;
	private String pk;

	public AbstractDAO(Class<E> type, String pk) {
		// 實際上vo的型別
		this.voType = type;
		this.pk = pk;
	}

	@Override
	public List<E> getAll() {
		Session session = HibernateSessionFactory.getSession();
		session.getTransaction().begin();
		List<E> list;
		try {
			list = session.createQuery("from " + voType.getName()).list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			list = new ArrayList<>();
		}
		return list;
	}

	@Override
	public E getOne(Integer id) {
		Session session = HibernateSessionFactory.getSession();
		session.getTransaction().begin();
		E vo = null;
		try {
			vo = (E) session.get(voType, id);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			try {
				vo = voType.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		return vo;
	}


	@Override
	public E create(E vo) {
		Session session = HibernateSessionFactory.getSession();
		try {
			vo.setCreateTime(new Date());
			session.beginTransaction();
			session.save(vo);
			session.getTransaction().commit();
			return vo;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public E update(E vo) {
		Session session = HibernateSessionFactory.getSession();
		try {
			vo.setLastModifyTime(new Date());
			session.beginTransaction();
			session.update(vo);
			session.getTransaction().commit();
			return vo;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(E vo) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			session.delete(vo);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Integer[] ids) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			session.createQuery("delete from " + voType.getSimpleName() + " vo where vo." + pk + " in (:ids)")
					.setParameterList("ids", ids).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<E> getHelper(String[] columnNames, Order order, Criterion... criterions) {
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		List<E> list;
		try {
			Criteria criteria = session.createCriteria(voType);

			if (criterions != null && criterions.length != 0) {
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
			}

			if (order != null) {
				criteria.addOrder(order);
			}

			// 將欲查詢的屬性欄位放到projectionList
			if (columnNames != null && columnNames.length != 0) {
				ProjectionList projectionList = Projections.projectionList();
				for (String columnName : columnNames) {
					// 第二個參數為別名, 這樣才能跟VO的屬性對應
					projectionList.add(Property.forName(columnName), columnName);
				}
				criteria.setProjection(projectionList);
				criteria.setResultTransformer(Transformers.aliasToBean(voType));
			}

			list = criteria.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			list = new ArrayList<>();
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<E> getHelper(Criterion... criterions) {
		return getHelper(null, null, criterions);
	}

	@Override
	public List<E> getHelper(String[] columnNames) {
		return getHelper(columnNames, null, new Criterion[] {});
	}

	@Override
	public List<E> getHelper(String[] columnNames, Criterion... criterions) {
		return getHelper(columnNames, null, criterions);
	}

}
