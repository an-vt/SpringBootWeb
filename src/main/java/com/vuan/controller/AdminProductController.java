package com.vuan.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vuan.model.CategoryDTO;
import com.vuan.model.ProductDTO;
import com.vuan.service.CategoryService;
import com.vuan.service.ProductService;

@Controller
public class AdminProductController {
	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@GetMapping(value = "/admin/product/add")
	public String AdminAddProductGet(Model model, HttpServletRequest request) {
		List<CategoryDTO> listCategoryDTOs = categoryService.getAll(0, 100);
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("listCategoryDTOs", listCategoryDTOs);

		return "admin/product/AddProduct";
	}

	@PostMapping(value = "/admin/product/add")
	public String AdminAddProductPost(Model model, @ModelAttribute(value = "product") ProductDTO productDTO,
			@RequestParam(name = "file") MultipartFile imagefile) {

		System.out.println("category id :" + productDTO.getCategoryDTO().getId());
		System.out.println("price :" + productDTO.getPrice());
		if (imagefile.getSize() > 0) {
			System.out.println("Them anh cho product");
			String originalFilename = imagefile.getOriginalFilename();
			int lastIndex = originalFilename.lastIndexOf(".");
			String ext = originalFilename.substring(lastIndex);

			String avatarFilename = System.currentTimeMillis() + ext;
			File newfile = new File("D:\\file\\user\\" + avatarFilename);
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(newfile);
				fileOutputStream.write(imagefile.getBytes());
				fileOutputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			productDTO.setImage(avatarFilename);
		} else if (imagefile.getSize() == 0) {
			System.out.println("khong them anh cho product");
			productDTO.setImage(null);
		}
		CategoryDTO categoryDTO = categoryService.get(productDTO.getCategoryDTO().getId());
		System.out.println("categoryDTO :" + categoryDTO.getId() + categoryDTO.getName());
		productDTO.setCategoryDTO(categoryDTO);
		productService.add(productDTO);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/edit")
	public String AdminEditProductGet(Model model, @RequestParam(value = "id") int id, HttpServletRequest request) {

		List<CategoryDTO> listCategoryDTOs = categoryService.getAll(0, 100);
		ProductDTO productDTO = productService.get(id);
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("listCategoryDTOs", listCategoryDTOs);
		return "admin/product/EditProduct";
	}

	@PostMapping(value = "/admin/product/edit")
	public String AdminEditProductPost(Model model, @ModelAttribute(value = "productDTO") ProductDTO productDTO,
			@RequestParam(name = "file") MultipartFile imagefile) {

		if (imagefile.getSize() > 0) {
			System.out.println("thay doi anh cho product");
			// ten avatar
			String originalFilename = imagefile.getOriginalFilename();
			System.out.println("ten avatar :" + originalFilename);
			//
			int lastIndex = originalFilename.lastIndexOf(".");
			System.out.println(lastIndex);
			// duoi anh .png ,.jpg
			String ext = originalFilename.substring(lastIndex);
			System.out.println(ext);
			System.out.println("So ngau nhien :" + System.currentTimeMillis());
			// lau so ngau nhien trong system
			String avatarFilename = System.currentTimeMillis() + ext;
			File newfile = new File("D:\\file\\user\\" + avatarFilename);
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(newfile);
				fileOutputStream.write(imagefile.getBytes());
				fileOutputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			productDTO.setImage(avatarFilename);
			productService.update(productDTO);
		} else if (imagefile.getSize() == 0) {
			System.out.println("khong thay doi anh cho product");
			productService.update(productDTO);
		}
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/delete")
	public String AdminDeleteProductGet(@RequestParam(value = "id") int id ,HttpServletRequest request ,
			@RequestParam(value = "page" ,required = false) int currentPage ,@RequestParam(value = "name" ,required = false) String name) {
		productService.delete(id);
		
		return "redirect:/admin/product/search?page="+currentPage+"&name="+name;
	}

	@GetMapping(value = "/admin/product/search")
	public String AdminSearchProductPost(Model model ,HttpServletRequest request, @RequestParam(value = "name" ,required = false) String name) {
		System.out.println("name :" + name);
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		
		if(name != null && !name.equals("null")) {
			System.out.println("Search");
			long count = productService.countSearch(name);
			double result = Math.ceil((double) count / 5);
			System.out.println("result :" + result);
	
			List<ProductDTO> listProductDTOs = productService.search(name, (page - 1) * 5, 5);
			
			if (listProductDTOs.isEmpty() ) {
				request.setAttribute("result", result);
				System.out.println("khong co ket qua");
			} else{
				System.out.println("co ket qua");
				request.setAttribute("result", result);
				request.setAttribute("currentPage", page);
				request.setAttribute("name", name);
				request.setAttribute("listProductDTOs", listProductDTOs);
			}
		}
		else {
			long count = productService.countGetAll();
			double result = Math.ceil((double) count / 5);
			System.out.println("result :" + result);

			List<ProductDTO> listProductDTOs = productService.getAll((page - 1) * 5, 5);
			model.addAttribute("result", result);
			request.setAttribute("currentPage", page);
			model.addAttribute("listProductDTOs", listProductDTOs);
		}
		return "admin/product/SearchProduct";
	}

}
