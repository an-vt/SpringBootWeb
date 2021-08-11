package com.vuan.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDTO {
	private int id;
	private String name;
	private int quantity;
	private Long price;
	private String image;
	private String description;
	private CategoryDTO categoryDTO;
	private MultipartFile file;
}
