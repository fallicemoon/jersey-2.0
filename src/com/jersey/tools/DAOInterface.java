package com.jersey.tools;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface DAOInterface<E extends AbstractVo> {
	
	public List<E> getAll();
	
	public List<E> getAll(Integer pageSize, Integer page);

	public Long getTotalCount(Criterion... criterions);

	public E getOne(String id);

	public E create(E vo);

	public E update(E vo);

	public E delete(E vo);

	public boolean delete(Integer[] ids);

	public List<E> getHelper(String[] columnNames, Order order, Criterion... criterions);

	public List<E> getHelper(Criterion... criterions);

	public List<E> getHelper(String[] columnNames);

	public List<E> getHelper(String[] columnNames, Criterion... criterions);
	
	public List<E> getHelper(Order order, Criterion...criterions);

}
