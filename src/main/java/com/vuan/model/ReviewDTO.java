package com.vuan.model;

import lombok.Data;

@Data
public class ReviewDTO {
	private int id;
	private int starNumber;
	private String reviewDate;
	private UserDTO userDTO;
	private ProductDTO productDTO;
}
