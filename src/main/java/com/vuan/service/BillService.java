package com.vuan.service;

import java.util.List;

import com.vuan.model.BillDTO;

public interface BillService {
	void add(BillDTO billDTO);

	void update(BillDTO billDTO);

	void delete(int id);

	BillDTO get(int id);

	List<BillDTO> searchByNameBuyer(String nameBuyer ,int start ,int length);

	List<BillDTO> searchByBuyerId(int buyerId ,int start ,int length);
	
	List<BillDTO> showAllBill(int start ,int length);
	
	long countSearchByNameBuyer(String nameBuyer);

	long countSearchByBuyerId(int buyerId);
	
	long countShowAllBill();
}
