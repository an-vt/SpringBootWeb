package com.vuan.dao.Iplm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CommentDao;
import com.vuan.entity.Comment;



@Transactional
@Repository
public class CommentDaoImpl implements CommentDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(Comment comment) {
		entityManager.persist(comment);
	}

	@Override
	public void update(Comment comment) {
		entityManager.merge(comment);
	}

	@Override
	public void delete(int id) {
		entityManager.remove(get(id));
	}

	@Override
	public Comment get(int id) {
		return entityManager.find(Comment.class, id);
	}

	@Override
	public List<Comment> searchByProduct(int id) {
		String jql = "select c from Comment c join c.product p join c.user u where p.id = :pId";
		return entityManager.createQuery(jql, Comment.class).setParameter("pId", id).getResultList();
	}

}
