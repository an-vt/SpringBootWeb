package com.vuan.service;

import java.util.List;

import com.vuan.model.ReviewDTO;
import com.vuan.model.SearchReviewDTO;


public interface ReviewService {
	
	void add(ReviewDTO reviewDTO);

	void delete(int id);

	void edit(ReviewDTO reviewDTO);

	ReviewDTO getById(int id);

	List<ReviewDTO> find(int id);

	Long count(SearchReviewDTO searchReviewDTO);

	Long coutTotal(SearchReviewDTO searchReviewDTO);

}
