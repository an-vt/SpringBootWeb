package com.vuan.dao;

import java.util.List;

import com.vuan.entity.BillProduct;

public interface BillProductDao {
	void add(BillProduct billProduct);

	void update(BillProduct billProduct);

	void delete(int id);

	BillProduct get(int id);

	List<BillProduct> searchByBillId(int idBill ,int start ,int length);
	
	long countSearchByBillId(int idBill);
}
