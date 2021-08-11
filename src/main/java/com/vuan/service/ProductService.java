package com.vuan.service;

import java.util.List;

import com.vuan.model.ProductDTO;

public interface ProductService {
	void add(ProductDTO productDTO);

	void update(ProductDTO productDTO);

	void delete(int id);

	ProductDTO get(int id);

	List<ProductDTO> search(String name ,int start ,int length);
	
	long countSearch(String name);

	List<ProductDTO> getAll(int start ,int maxPerPage);
	
	long countGetAll();
	
	List<ProductDTO> filterProduct(String nameCategory ,long fromPrice ,long toPrice ,int start ,int length);
	
	long countFilterProduct(String nameCategory ,long fromPrice ,long toPrice);
}
