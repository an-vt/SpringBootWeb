package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CouponDao;
import com.vuan.entity.Coupon;

@Repository
@Transactional
public class CouponDaoIplm implements CouponDao{
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void add(Coupon coupon) {
		entityManager.persist(coupon);
	}

	@Override
	public void edit(Coupon coupon) {
		entityManager.merge(coupon);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}
	
	@Override
	public Coupon get(int id) {
		return entityManager.find(Coupon.class, id);
	}

	@Override
	public List<Coupon> searchByCode(String code ,int start ,int length) {
		String jql = "SELECT coupon FROM Coupon coupon WHERE coupon.code LIKE :code";
		Query query = entityManager.createQuery(jql ,Coupon.class).setParameter("code","%"+code+"%" ).setFirstResult(start).setMaxResults(length);
		return query.getResultList();
	}

	@Override
	public List<Coupon> showAll(int start ,int length) {
		String jql = "select c from Coupon c";
		return entityManager.createQuery(jql, Coupon.class).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public long countShowAll() {
		String jql = "select count(c) from Coupon c";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}

	@Override
	public long countSearchByCode(String code) {
		String jql = "SELECT count(coupon) FROM Coupon coupon WHERE coupon.code LIKE :code";
		return entityManager.createQuery(jql, Long.class).setParameter("code","%"+code+"%").getSingleResult();
	}

	@Override
	public Coupon getByCode(String code) {
		String jql = "SELECT coupon FROM Coupon coupon WHERE coupon.code = :code";
		Query query = entityManager.createQuery(jql ,Coupon.class).setParameter("code", code );
		return (Coupon) query.getSingleResult();
	}

}
