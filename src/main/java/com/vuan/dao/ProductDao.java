package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Product;

public interface ProductDao {
	void add(Product product);

	void update(Product product);

	void delete(int id);

	Product get(int id);

	List<Product> search(String name ,int start ,int length);

	List<Product> getAll(int start ,int length);
	
	List<Product> filterProduct(String nameCategory ,long fromPrice ,long toPrice ,int start ,int length);
	
	long countSearch(String name);
	
	long countGetAll();
	
	long countFilterProduct(String nameCategory ,long fromPrice ,long toPrice);
}
