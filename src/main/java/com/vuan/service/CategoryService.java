package com.vuan.service;

import java.util.List;

import com.vuan.model.CategoryDTO;

public interface CategoryService {
	void add(CategoryDTO categoryDTO);

	void update(CategoryDTO categoryDTO);

	void delete(int id);

	CategoryDTO get(int id);

	List<CategoryDTO> search(String name ,int start ,int length);

	List<CategoryDTO> getAll(int start ,int length);
	
	long countSearch(String name);
	
	long countGetAll();
}
