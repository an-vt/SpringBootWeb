package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.BillDao;
import com.vuan.entity.Bill;

@Transactional
@Repository
public class BillDaoImpl implements BillDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void add(Bill bill) {
		entityManager.persist(bill);
	}

	@Override
	public void update(Bill bill) {
		entityManager.merge(bill);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));

	}

	@Override
	public Bill get(int id) {
		return entityManager.find(Bill.class, id);
	}

	@Override
	public List<Bill> searchByNameBuyer(String nameBuyer ,int start ,int length) {
		String jql = "select b from Bill b join b.buyer u where u.name like :uname";
		return entityManager.createQuery(jql, Bill.class).setParameter("uname", "%" + nameBuyer + "%").setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<Bill> searchByBuyerId(int buyerId ,int start ,int length) {
		String jql = "select b from Bill b join b.buyer u where u.id =:buyerId";
		return entityManager.createQuery(jql, Bill.class).setParameter("buyerId", buyerId).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<Bill> showAllBill(int start ,int length) {
		String jql = "select b from Bill b";
		return entityManager.createQuery(jql, Bill.class).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public long countSearchByNameBuyer(String nameBuyer) {
		String jql = "select count(b) from Bill b join b.buyer u where u.name like :uname";
		return entityManager.createQuery(jql, Long.class).setParameter("uname", "%" + nameBuyer + "%").getSingleResult();
	}

	@Override
	public long countSearchByBuyerId(int buyerId) {
		String jql = "select count(b) from Bill b join b.buyer u where u.id =:buyerId";
		return entityManager.createQuery(jql, Long.class).setParameter("buyerId", buyerId ).getSingleResult();
	}

	@Override
	public long countShowAllBill() {
		String jql = "select count(b) from Bill b join b.buyer u";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

}
