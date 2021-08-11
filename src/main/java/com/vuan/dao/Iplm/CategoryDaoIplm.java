package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CategoryDao;
import com.vuan.entity.Category;

import net.bytebuddy.implementation.Implementation;

@Repository //lam viec voi csdl
@Transactional // quan ly giao dich.
//dam bao tat ca cac ham deu thanh cong hoac faile
public class CategoryDaoIplm implements CategoryDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Category category) {
		entityManager.persist(category);
	}

	@Override
	public void update(Category category) {
		entityManager.merge(category);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}

	@Override
	public Category get(int id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public List<Category> search(String name ,int start ,int length) {
		String jql="SELECT c FROM Category c WHERE c.name LIKE :name";
		return entityManager.createQuery(jql, Category.class).setParameter("name", "%" + name + "%").setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<Category> getAll(int start ,int length) {
		String jql="SELECT c FROM Category c";
		return entityManager.createQuery(jql, Category.class).setFirstResult(start).setMaxResults(length).getResultList();
	}
	
	@Override
	public long countSearch(String name) {
		String jql="SELECT count(c) FROM Category c WHERE c.name LIKE :name";
		return entityManager.createQuery(jql, Long.class).setParameter("name", "%" + name + "%").getSingleResult();
	}

	@Override
	public long countGetAll() {
		String jql="SELECT count(c) FROM Category c";
		return entityManager.createQuery(jql, Long.class).getSingleResult();
	}
}
