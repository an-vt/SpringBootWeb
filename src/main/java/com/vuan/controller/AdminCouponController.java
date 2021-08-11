package com.vuan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vuan.model.CouponDTO;
import com.vuan.service.CouponService;

@Controller
public class AdminCouponController {
	@Autowired
	private CouponService couponService;

	@GetMapping(value = "/admin/coupon/add")
	public String AdminAddCouponGet() {
		return "admin/coupon/AddCoupon";
	}

	@PostMapping(value = "/admin/coupon/add")
	public String AdminAddCouponPost(Model model, @ModelAttribute(value = "couponDTO") CouponDTO couponDTO) {
		couponService.add(couponDTO);
		return "redirect:/admin/coupon/search";
	}
	
	@GetMapping(value = "/admin/coupon/edit")
	public String AdminEditCouponGet(Model model, @RequestParam(value = "id") int id) {
		CouponDTO couponDTO = couponService.get(id);
		model.addAttribute("couponDTO", couponDTO);
		return "admin/coupon/EditCoupon";
	}

	@PostMapping(value = "/admin/coupon/edit")
	public String AdminEditCouponPost(@ModelAttribute(value = "couponDTO") CouponDTO couponDTO) {
		couponService.edit(couponDTO);
		return "redirect:/admin/coupon/search";
	}

	@GetMapping(value = "/admin/coupon/search")
	public String AdminSearchCouponPost(HttpServletRequest request, @RequestParam(value = "name" ,required = false) String name) {
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		
		if(name !=  null && !name.equals("null")) {
			System.out.println("search coupon"+name);
			long count = couponService.countSearchByCode(name);
			double result = Math.ceil((double) count/5);
			List<CouponDTO> listCouponDTOs = couponService.searchByCode(name ,(page-1)*5 ,5);
			if(listCouponDTOs.isEmpty()) {
				System.out.println("khong tim thay name coupon nao");
				request.setAttribute("result", result);
			}
			else {
				request.setAttribute("name", name);
				request.setAttribute("currentPage", page);
				request.setAttribute("listCouponDTOs", listCouponDTOs);	
				request.setAttribute("result", result);
			}
		}
		else {
			long count = couponService.countShowAll();
			double result = Math.ceil((double) count/5);
			List<CouponDTO> listCouponDTOs = couponService.showAll((page-1)*5 ,5);
			request.setAttribute("listCouponDTOs", listCouponDTOs);
			request.setAttribute("currentPage", page);
			request.setAttribute("result", result);
		}
		
		return "admin/coupon/SearchCoupon";
	}
	
	@GetMapping(value = "/admin/coupon/delete")
	public String AdminDeleteCoupon(Model model, @RequestParam(value = "id") int id ,
			@RequestParam(value = "page" ,required = false) int currentPage ,@RequestParam(value = "name" ,required = false) String name) {
		couponService.delete(id);
		return "redirect:/admin/coupon/search?page="+currentPage+"&name="+name;
	}
}
