package com.vuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vuan.model.BillProductDTO;
import com.vuan.service.BillProductService;

@Controller
public class AdminBillProductController {
	@Autowired
	BillProductService billProductService;
	
	@GetMapping(value = "/admin/billproduct/search-bill")
	public String AdminSearchBillGet(@RequestParam(value = "id") int id ,HttpServletRequest request) {
		System.out.println("id bill "+id);
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		List<BillProductDTO> listBillProductDTOs = billProductService.searchByBillId(id ,(page-1)*5 ,5);
		for (BillProductDTO billProductDTO : listBillProductDTOs) {
			System.out.println(billProductDTO);
		}
		long count = billProductService.countSearchByBillId(id);
		double result = Math.ceil((double) count / 5);
		
		request.setAttribute("listBillProductDTOs", listBillProductDTOs);
		request.setAttribute("result", result);
		
		return "admin/billproduct/SearchBillProduct";
	}
}
