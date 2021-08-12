package com.vuan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vuan.model.BillProductDTO;
import com.vuan.model.CategoryDTO;
import com.vuan.model.CommentDTO;
import com.vuan.model.CouponDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.ReviewDTO;
import com.vuan.model.UserAddressDTO;
import com.vuan.model.UserDTO;
import com.vuan.service.CategoryService;
import com.vuan.service.CommentService;
import com.vuan.service.CouponService;
import com.vuan.service.ProductService;
import com.vuan.service.ReviewService;
import com.vuan.service.UserAddressService;
import com.vuan.service.UserService;

@Controller
public class ClientController { 
	@Autowired
	ProductService productService;
	
	@Autowired
	CouponService couponService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserAddressService userAddressService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/")
	public String ClientIndexGet(Model model ,@RequestParam(name = "currentPage" ,required = false) String currentPage) {
		List<ProductDTO> listProductDTOs = productService.getAll(0 ,100);
		if(currentPage != null){
			System.out.println("forward page checkout");
			return "redirect:/checkout";
		}
		if(listProductDTOs != null) {
			System.out.println("co san pham");
			model.addAttribute("listProductDTOs", listProductDTOs);
		}

		return "templates/client/index";
	} 
	
//	@GetMapping(value = "/member/home")
//	public String memberHome() {
//		return "redirect:/";
//	} 
	
	@PostMapping(value = "/register")
	public String register(@ModelAttribute UserDTO userDTO) {
		userDTO.setEnabled(true);
		userDTO.setRole("ROLE_MEMBER");
		userService.add(userDTO);
		return "redirect:/login"; 
	} 

	@GetMapping(value = "/login")
	public String ClientLoginGet(Model model ,@RequestParam(name = "e" ,required = false) String error,
			@RequestParam(name = "currentPage" ,required = false) String currentPage) {	
		if(error != null){
			System.out.println("Co loi xay ra");
			model.addAttribute("error", error);
		}
		if(currentPage != null){
			System.out.println("forward page checkout");
			return "redirect:/checkout";
		}
		
		return "client/login";
	}
	
//	@PostMapping(value = "/login-member")
//	public String ClientLoginGet(Model model ,@RequestParam(name = "currentPage" ,required = false) String currentPage) {	
//		System.out.println("ohoh");
//		if(currentPage != null){
//			System.out.println("forward page checkout");
//			return "redirect:/checkout";
//		}
//		
//		return "redirect:/index";
//	}
	
	@GetMapping(value = "/product-detail")
	public String ClientDetailProductGet(HttpServletRequest request ,@RequestParam(value = "id") int idP) {
		ProductDTO productDTO = productService.get(idP);
		List<CommentDTO> listCommentDTOs = commentService.searchByProduct(idP);
		
		List<ReviewDTO> listReviewDTOs = reviewService.find(idP);
		
		request.setAttribute("listReviewDTOs", listReviewDTOs);
		request.setAttribute("quantityReview", listReviewDTOs.size());
		request.setAttribute("quantityComment", listCommentDTOs.size());
		request.setAttribute("listCommentDTOs", listCommentDTOs);
		request.setAttribute("productDTO", productDTO);
		
		return "client/product_detail_page";
	}
	
	@GetMapping(value = "/cart/delete")
	public String deleteItem(HttpSession httpSession ,HttpServletRequest request ,@RequestParam(value = "id") int idP) {
		System.out.println("delete product "+idP);
		Object object = httpSession.getAttribute("cart");
		if(object != null) {
			System.out.println("gio hang co");
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;
			map.remove(idP);
			httpSession.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/cart/update")
	public String updateQuantityProduct(HttpSession httpSession ,HttpServletRequest request ,@RequestParam(value = "id") int idP 
			,@RequestParam(value = "quantity") int quantity) {
		System.out.println("update product "+idP);
		System.out.println("quantitty update "+quantity);
		Object object = httpSession.getAttribute("cart");
		if(object != null) {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;
			BillProductDTO billProductDTO = map.get(idP);

			billProductDTO.setQuantity(quantity);
			
			httpSession.setAttribute("cart", map);
		}
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/add-to-cart")
	public String ClientAddToCartGet(HttpSession httpSession,@RequestParam(value = "id") int id ) {
		ProductDTO productDTO = productService.get(id);
		if(productDTO != null) {
			System.out.println("co san pham");
		}else {
			System.out.println("khong co san pham");
		}
		Object object = httpSession.getAttribute("cart");
		
		//gio hang chong
		if(object == null) {
			//tao mat hang
			BillProductDTO billProductDTO=new BillProductDTO();
			
			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setUnitPrice(productDTO.getPrice());
			billProductDTO.setQuantity(1);
			
			//them product vao gio hang
			Map<Integer, BillProductDTO> map=new HashMap<>();
			map.put(id, billProductDTO);
			
			httpSession.setAttribute("cart", map);
		}
		//neu gio hang da co mat hang
		else {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;
			BillProductDTO billProductDTO = map.get(id);
			
			if(billProductDTO == null) {
				billProductDTO =new BillProductDTO();
				billProductDTO.setProductDTO(productDTO);
				billProductDTO.setQuantity(1);
				billProductDTO.setUnitPrice(productDTO.getPrice());
				
				map.put(id, billProductDTO);
			}
			else {
				billProductDTO.setQuantity(billProductDTO.getQuantity()+1);
			}
			
			httpSession.setAttribute("cart", map);
		}
		
		return "redirect:/cart";
	}
	
	@PostMapping(value = "/add-to-cart")
	public String ClientAddToCartPost(HttpSession httpSession,@RequestParam(value = "id") int id ,@RequestParam(value = "product_quantity") int product_quantity) {
		System.out.println("add-to-cart post:id"+id);
		ProductDTO productDTO = productService.get(id);
		Object object = httpSession.getAttribute("cart");
		
		if(object == null) {
			//tao mat hang
			BillProductDTO billProductDTO=new BillProductDTO();
			
			billProductDTO.setProductDTO(productDTO);
			billProductDTO.setUnitPrice(productDTO.getPrice());
			billProductDTO.setQuantity(product_quantity);
			
			//them product vao gio hang
			Map<Integer, BillProductDTO> map=new HashMap<>();
			map.put(id, billProductDTO);
			
			httpSession.setAttribute("cart", map);
		}
		else {
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) object;
			BillProductDTO billProductDTO = map.get(id);
			
			if(billProductDTO == null) {
				billProductDTO =new BillProductDTO();
				billProductDTO.setProductDTO(productDTO);
				billProductDTO.setQuantity(product_quantity);
				billProductDTO.setUnitPrice(productDTO.getPrice());
				
				map.put(id, billProductDTO);
			}
			else {
				billProductDTO.setQuantity(billProductDTO.getQuantity()+product_quantity);
			}
			
			httpSession.setAttribute("cart", map);
		}
		
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/cart")
	public String ClientCartGet(HttpSession httpSession ,Model model) {
		Object obj = httpSession.getAttribute("cart");
//		CouponDTO couponDTO = (CouponDTO) httpSession.getAttribute("coupon");
		if(obj != null) {
			System.out.println("cart co");
			int total =0;
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) obj;
			
			//cart rong
			if(map.isEmpty()) {
				model.addAttribute("total", 0);
			}else {
				for (Map.Entry<Integer, BillProductDTO> entry : map.entrySet()) {
				    BillProductDTO billProductDTO = entry.getValue();

				    total += billProductDTO.getQuantity() * billProductDTO.getUnitPrice(); 
				    
				    
//				    if(couponDTO != null) {
//				    	int totalUpdate=(int) (total-(total*((float) couponDTO.getPresent()/100 ) ));
//						System.out.println("Total update:"+totalUpdate);
//						model.addAttribute("total", totalUpdate);
//				    }
				    model.addAttribute("total", total);
				    
				}	
			}
		}
		return "client/cart_page";
	}
	
	@GetMapping(value = "/checkout")
	public String ClientCheckoutGet(HttpSession httpSession ,Model model ,HttpServletRequest request) {
		
		Object obj = httpSession.getAttribute("cart");
		CouponDTO couponDTO=(CouponDTO) httpSession.getAttribute("coupon");
		int total =0;
		if (obj != null) {
			List<UserAddressDTO> listUserAddressDTOs = userAddressService.getAll();
			
			Map<Integer, BillProductDTO> map = (Map<Integer, BillProductDTO>) obj;

			for (Map.Entry<Integer, BillProductDTO> entry : map.entrySet()) {
				BillProductDTO billProductDTO = entry.getValue();

				total += billProductDTO.getQuantity() * billProductDTO.getUnitPrice();
			}
			
			System.out.println("total :"+total);
			if(couponDTO != null) {
				int totalCoupon=(int) (total*((float) couponDTO.getPresent()/100));
				
				model.addAttribute("total", total);
				model.addAttribute("totalCoupon", totalCoupon);
				model.addAttribute("listUserAddressDTOs", listUserAddressDTOs);
			}
			else {
				model.addAttribute("total", total);
				model.addAttribute("listUserAddressDTOs", listUserAddressDTOs);
			}
		}	
		
		
		return "client/checkout_page";
	}
	
	@PostMapping(value = "/coupon")
	public String ClientCouponGet(@RequestParam(value = "coupon") String code ,HttpSession httpSession) {
		if(!code.trim().isEmpty()) {
			CouponDTO couponDTO = couponService.getByCode(code);
			if(couponDTO != null) {
				httpSession.setAttribute("coupon", couponDTO);
			}
		}else {
			httpSession.removeAttribute("coupon");
		}
		
		return "redirect:/cart";
	}
	
	@GetMapping(value = "/list-product")
	public String ClientFilterProductPost(HttpServletRequest request ,@RequestParam(value = "nameCategory" ,required = false) String nameCategory ,
			@RequestParam(value = "priceProduct" ,required = false) String priceProduct) {
		System.out.println("nameC "+nameCategory);
		System.out.println("priceC "+priceProduct);
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		System.out.println("page curent :"+page);
		long count=0;
		
			List<CategoryDTO> listCategoryDTOs = categoryService.getAll(0, 50);
			if(priceProduct!= null && priceProduct.equals("default")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 0, 10000, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 0, 10000);
			}else if(priceProduct!= null && priceProduct.equals("smaller1")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 0, 100, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 0, 100);
			}else if(priceProduct!= null && priceProduct.equals("between1and2")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 100, 200, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 100, 200);
			}else if(priceProduct!= null && priceProduct.equals("between2and3")) {
				System.out.println("xay ra 2-3");
				count=productService.countFilterProduct(nameCategory, 200, 300);
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 200, 300, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay23");
				}else if(listCategoryDTOs.size() > 0){
					System.out.println("co tim thay23");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}	
			}else if(priceProduct!= null && priceProduct.equals("between3and4")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 300, 400, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 300, 400);
			}else if(priceProduct!= null && priceProduct.equals("between4and5")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 400, 500, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 400, 500);
			}else if(priceProduct!= null && priceProduct.equals("biger5")) {
				List<ProductDTO> listProductDTOs = productService.filterProduct(nameCategory, 500, 10000, (page-1)*9, 9);
				if(listCategoryDTOs.isEmpty()) {
					System.out.println("khong tim thay");
				}else {
					System.out.println("co tim thay");
					request.setAttribute("listProductDTOs", listProductDTOs);
				}
				count=productService.countFilterProduct(nameCategory, 500, 10000);
			}else {
				List<ProductDTO> listProductDTOs = productService.getAll((page-1)*9, 9);
				request.setAttribute("listProductDTOs", listProductDTOs);
				count = productService.countGetAll();
			}
			request.setAttribute("listCategoryDTOs", listCategoryDTOs);

		
		double result = Math.ceil((double) count/9);
		request.setAttribute("result", result);
		request.setAttribute("currentPage", page);
		
		return "client/category_page";
	}
}
