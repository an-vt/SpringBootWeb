package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Category;

import net.bytebuddy.implementation.bind.annotation.Super;


public interface CategoryDao {
	
	void add(Category category);

	void update(Category category);

	void delete(int id);

	Category get(int id);

	List<Category> search(String name ,int start ,int length);

	List<Category> getAll(int start ,int length);
	
	long countSearch(String name);
	
	long countGetAll();
}