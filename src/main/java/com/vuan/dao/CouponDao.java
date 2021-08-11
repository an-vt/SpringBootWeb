package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Coupon;

public interface CouponDao {
	void add(Coupon coupon);
	
	void edit(Coupon coupon);

	void delete(int id);
	
	Coupon get(int id);
	
	Coupon getByCode(String code);
	
	List<Coupon> searchByCode(String code ,int start ,int length);// duy nhat
	
	List<Coupon> showAll(int start ,int length);
	
	long countShowAll();
	
	long countSearchByCode(String code);
}
