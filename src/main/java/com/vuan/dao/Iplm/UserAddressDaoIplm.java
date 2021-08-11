package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.vuan.dao.UserAddressDao;
import com.vuan.entity.UserAddress;

@Repository //lam viec voi csdlO
public class UserAddressDaoIplm implements UserAddressDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void add(UserAddress userAddress) {
		entityManager.persist(userAddress);
	}

	@Override
	public void update(UserAddress userAddress) {
		entityManager.merge(userAddress);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}

	@Override
	public UserAddress get(int id) {
		return entityManager.find(UserAddress.class, id);
	}

	@Override
	public UserAddress getByUserId(int userId ) {
		String jql="SELECT ud FROM UserAddress ud WHERE ud.id = :userId";
		return entityManager.createQuery(jql, UserAddress.class).setParameter("userId", userId).getSingleResult();
	}
	@Override
	public List<UserAddress> getAll() {
		String jql = "SELECT ud FROM UserAddress ud ";
		return entityManager.createQuery(jql, UserAddress.class).getResultList();
	}
	
}
