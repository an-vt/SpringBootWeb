package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.BillProductDao;
import com.vuan.entity.BillProduct;

@Transactional
@Repository
public class BillProductDaoImpl implements BillProductDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void add(BillProduct billProduct) {
		entityManager.persist(billProduct);
	}

	@Override
	public void update(BillProduct billProduct) {
		entityManager.merge(billProduct);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(id);
	
	}

	@Override
	public BillProduct get(int id) {
		return entityManager.find(BillProduct.class, id);
	}

	@Override
	public List<BillProduct> searchByBillId(int idBill ,int start ,int length) {
		String jql = "select bp from BillProduct bp join bp.product p join bp.bill b where b.id =:billId";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("billId", idBill).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public long countSearchByBillId(int idBill) {
		String jql = "select count(bp) from BillProduct bp join bp.product p join bp.bill b where b.id =:billId";
		return entityManager.createQuery(jql, Long.class).setParameter("billId", idBill).getSingleResult();
	}

}
