package com.vuan.dao;

import java.util.List;

import com.vuan.entity.Bill;

public interface BillDao {
	void add(Bill bill);

	void update(Bill bill);

	void delete(int id);

	Bill get(int id);

	List<Bill> searchByNameBuyer(String nameBuyer ,int start ,int length);

	List<Bill> searchByBuyerId(int buyerId ,int start ,int length);
	
	List<Bill> showAllBill(int start ,int length);
	
	long countSearchByNameBuyer(String nameBuyer);

	long countSearchByBuyerId(int buyerId);
	
	long countShowAllBill();
}
