package com.vuan.service.Iplm;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.ReviewDao;
import com.vuan.entity.Product;
import com.vuan.entity.Review;
import com.vuan.entity.User;
import com.vuan.model.ProductDTO;
import com.vuan.model.ReviewDTO;
import com.vuan.model.SearchReviewDTO;
import com.vuan.model.UserDTO;
import com.vuan.service.ReviewService;
import com.vuan.utils.DateTimeUtils;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	@Override
	public void add(ReviewDTO reviewDTO) {
		Review review=new Review();
		review.setReviewDate(new Date());
		review.setStarNumBer(reviewDTO.getStarNumber());
		review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
		User user= new User();
		user.setName(reviewDTO.getUserDTO().getName());
		user.setId(reviewDTO.getUserDTO().getId());
		review.setUser(user);
		reviewDao.add(review);

	}

	@Override
	public void delete(int id) {
		Review review = reviewDao.getById(id);
		if (review != null) {
			reviewDao.delete(review);
		}

	}

	@Override
	public void edit(ReviewDTO reviewDTO) {
		Review review = reviewDao.getById(reviewDTO.getId());
		if (review != null) {
			review.setStarNumBer(reviewDTO.getStarNumber());
			review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
			User user= new User();
			user.setName(reviewDTO.getUserDTO().getName());
			review.setUser(user);
		}
		reviewDao.edit(review);
	}

	@Override
	public ReviewDTO getById(int id) {
		Review review = reviewDao.getById(id);
		if (review != null) {
			convert(review);
		}
		return null;
	}

	private ReviewDTO convert(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(review.getId());
		reviewDTO.setReviewDate(DateTimeUtils.formatDate(review.getReviewDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		reviewDTO.setStarNumber(review.getStarNumBer());
		
		ProductDTO productDTO= new ProductDTO();
		productDTO.setId(review.getProduct().getId());
		reviewDTO.setProductDTO(productDTO);
		
		UserDTO userDTO= new UserDTO();
		userDTO.setName(review.getUser().getName());
		userDTO.setAvatar(review.getUser().getAvatar());
		reviewDTO.setUserDTO(userDTO);
		
		return reviewDTO;
	}

	@Override
	public List<ReviewDTO> find(int productId) {
		List<Review> reviews = reviewDao.find(productId);
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		reviews.forEach(rev -> {
			reviewDTOs.add(convert(rev));
		});
		return reviewDTOs;
	}

	@Override
	public Long count(SearchReviewDTO searchReviewDTO) {

		return reviewDao.count(searchReviewDTO);
	}

	@Override
	public Long coutTotal(SearchReviewDTO searchReviewDTO) {

		return reviewDao.count(searchReviewDTO);
	}

}
