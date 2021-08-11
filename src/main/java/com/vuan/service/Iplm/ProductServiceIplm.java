package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.ProductDao;
import com.vuan.entity.Category;
import com.vuan.entity.Product;
import com.vuan.model.CategoryDTO;
import com.vuan.model.ProductDTO;
import com.vuan.service.ProductService;

@Service
@Transactional
public class ProductServiceIplm implements ProductService{
	@Autowired
	ProductDao productDao;
	
	@Override
	public void add(ProductDTO productDTO) {
		Product product=new Product();
		Category category=new Category();
		
		product.setName(productDTO.getName());
		product.setQuantity(productDTO.getQuantity());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		
		category.setId(productDTO.getCategoryDTO().getId());
		category.setName(productDTO.getCategoryDTO().getName());
		
		product.setCategory(category);
		
		productDao.add(product);
		productDTO.setId(product.getId());
	}

	@Override
	public void update(ProductDTO productDTO) {
		Category category=new Category();
		
		Product product=productDao.get(productDTO.getId());
		if(product != null) {
			product.setId(productDTO.getId());
			product.setName(productDTO.getName());
			product.setQuantity(productDTO.getQuantity());
			product.setPrice(productDTO.getPrice());
			product.setDescription(productDTO.getDescription());
			product.setImage(productDTO.getImage());
			
			category.setId(productDTO.getCategoryDTO().getId());
			product.setCategory(category);
			
			productDao.update(product);
		}
		
	}

	@Override
	public void delete(int id) {
		Product product=productDao.get(id);
		if(product != null) {
			productDao.delete(id);
		}
	}

	@Override
	public ProductDTO get(int id) {
		Product product=productDao.get(id);
		return convert(product);
	}

	@Override
	public List<ProductDTO> search(String name ,int start ,int length) {
		List<Product> listProducts=productDao.search(name ,start ,length);
		List<ProductDTO> listProductDTOs=new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			listProductDTOs.add(convert(product));
		}
		return listProductDTOs;
	}

	@Override
	public List<ProductDTO> getAll(int start ,int length) {
		List<Product> listProducts=productDao.getAll(start ,length);
		List<ProductDTO> listProductDTOs=new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			listProductDTOs.add(convert(product));
		}
		return listProductDTOs;
	}
	
	private ProductDTO convert(Product product) {
		ProductDTO productDTO=new ProductDTO();
		CategoryDTO categoryDTO=new CategoryDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setPrice(product.getPrice());
		productDTO.setImage(product.getImage());
		productDTO.setDescription(product.getDescription());
		
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());
		
		productDTO.setCategoryDTO(categoryDTO);
		return productDTO;
	}

	@Override
	public long countGetAll() {
		long count = productDao.countGetAll();
		return count;
	}

	@Override
	public long countSearch(String name) {
		long count = productDao.countSearch(name);
		return count;
	}

	@Override
	public List<ProductDTO> filterProduct(String nameCategory, long fromPrice, long toPrice, int start, int length) {
		List<Product> listProducts = productDao.filterProduct(nameCategory, fromPrice, toPrice, start, length);
		List<ProductDTO> listProductDTOs=new ArrayList<ProductDTO>();
		if(listProducts.isEmpty()) {
			System.out.println("service khong co san pham nao");
		}
		else {
			System.out.println("service co sp");
			for (Product product : listProducts) {
				listProductDTOs.add(convert(product));
			}
		}
		return listProductDTOs;
	}

	@Override
	public long countFilterProduct(String nameCategory, long fromPrice, long toPrice) {
		long count = productDao.countFilterProduct(nameCategory, fromPrice, toPrice);
		if(count == 0) {
			System.out.println("count khong co");
		}else {
			System.out.println("count co");
		}
		return count;
	}
}
