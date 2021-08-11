package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.UserDao;
import com.vuan.entity.User;

@Repository //lam viec voi csdl
@Transactional // quan ly giao dich.
//dam bao tat ca cac ham deu thanh cong hoac faile
public class UserDaoIplm implements UserDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void add(User user) {
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}

	@Override
	public User get(int id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getByUserName(String username) {
		String jql="SELECT u FROM User u WHERE u.username = :name";
		return entityManager.createQuery(jql, User.class).setParameter("name", username).getSingleResult();
	}

	@Override
	public List<User> search(String findName, int start, int length) {
		String jql="SELECT u FROM User u WHERE u.name LIKE :name";
		return entityManager.createQuery(jql, User.class).setParameter("name", "%" + findName + "%").setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<User> getAll(int start ,int length) {
		String jql = "SELECT u FROM User u ";
		return entityManager.createQuery(jql, User.class).setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public long countSearch(String name) {
		String jql="SELECT count(u) FROM User u WHERE u.name LIKE :name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", "%" + name + "%").getSingleResult();
	}

	@Override
	public long countGetAll() {
		String jql="SELECT count(u) FROM User u";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}
	
}
