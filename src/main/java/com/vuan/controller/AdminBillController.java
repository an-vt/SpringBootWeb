package com.vuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vuan.model.BillDTO;
import com.vuan.service.BillService;


@Controller
public class AdminBillController {
	@Autowired
	BillService billService;
	
	@GetMapping(value = "/admin/bill/search")
	public String AdminShowAllBillGet(HttpServletRequest request ,@RequestParam(value = "nameBuyer" ,required = false) String nameBuyer) {
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		
		if(nameBuyer != null && !nameBuyer.equals("null")) {
			long count = billService.countSearchByNameBuyer(nameBuyer);
			System.out.println("count bill :"+count);
			double result = Math.ceil((double) count/5);
			List<BillDTO> listBillDTOs=billService.searchByNameBuyer(nameBuyer, (page-1)*5, 5);
			if(listBillDTOs.isEmpty()) {
				System.out.println("khong co ket qua");
				request.setAttribute("result", result);
			}
			else {
				System.out.println("co ket qua");
				request.setAttribute("result", result);
				request.setAttribute("currentPage", page);
				request.setAttribute("name", nameBuyer);
				request.setAttribute("listBillDTOs", listBillDTOs);
			}
		}
		else {
			long count = billService.countShowAllBill();
			double result = Math.ceil((double) count/5);
			List<BillDTO> listBillDTOs = billService.showAllBill((page-1)*5 ,5);
			request.setAttribute("listBillDTOs", listBillDTOs);
			request.setAttribute("currentPage", page);
			request.setAttribute("result", result);
		}
		return "admin/bill/SearchBill";
	}
	
	@GetMapping(value = "/admin/bill/delete")
	public String AdminDeleteUser(HttpServletRequest request, @RequestParam(value = "id") int id ,
			@RequestParam(value = "page" ,required = false) int currentPage ,@RequestParam(value = "name" ,required = false) String name) {
		billService.delete(id);
		return "redirect:/admin/bill/search?page="+currentPage+"&name="+name;
	}
}
