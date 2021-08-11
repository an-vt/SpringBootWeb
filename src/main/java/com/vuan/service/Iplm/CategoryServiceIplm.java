package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CategoryDao;
import com.vuan.entity.Category;
import com.vuan.model.CategoryDTO;
import com.vuan.service.CategoryService;

@Service
@Transactional
public class CategoryServiceIplm implements CategoryService{
	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public void add(CategoryDTO categoryDTO) {
		Category category=new Category();
		category.setName(categoryDTO.getName());
		
		categoryDao.add(category);
		
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryDao.get(categoryDTO.getId());
		if(category != null) {
			category.setId(categoryDTO.getId());
			category.setName(categoryDTO.getName());
			
			categoryDao.update(category);
		}
	}

	@Override
	public void delete(int id) {
		Category category = categoryDao.get(id);
		if(category != null) {
			categoryDao.delete(id);
		}	
	}

	@Override
	public CategoryDTO get(int id) {
		Category category = categoryDao.get(id);
		return convert(category);
	}

	@Override
	public List<CategoryDTO> search(String name ,int start ,int length) {
		List<Category> listCategorys=categoryDao.search(name ,start ,length);
		List<CategoryDTO> listCategoryDTOs=new ArrayList<CategoryDTO>();
		for (Category category : listCategorys) {
			listCategoryDTOs.add(convert(category));
		}
		return listCategoryDTOs;
	}

	@Override
	public List<CategoryDTO> getAll(int start ,int length) {
		List<Category> listCategorys=categoryDao.getAll(start ,length);
		List<CategoryDTO> listCategoryDTOs=new ArrayList<CategoryDTO>();
		for (Category category : listCategorys) {
			listCategoryDTOs.add(convert(category));
		}
		return listCategoryDTOs;
	}
	
	public CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO=new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		return categoryDTO;
	}
	
	@Override
	public long countSearch(String name) {
		long count=categoryDao.countSearch(name);
		return count;
	}

	@Override
	public long countGetAll() {
		long count=categoryDao.countGetAll();
		return count;
	}
}
