package com.vuan.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vuan.model.UserDTO;
import com.vuan.service.StorageService;
import com.vuan.service.UserService;
import com.vuan.vadilator.UserDTOVadilator;

@Controller
public class AdminUserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private UserDTOVadilator userDTOVadilator;

	@GetMapping(value = "/admin/user/add")
	public String AdminAddUserGet(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "admin/user/AddUser";
	}

	@PostMapping(value = "/admin/user/add")
	public String AdminAddUserPost(Model model, @ModelAttribute UserDTO userDTO,
			@RequestParam(name = "file") MultipartFile imagefile, BindingResult bindingResult) {
		userDTOVadilator.validate(userDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("userDTO", userDTO);
			return "admin/user/AddUser";
		} else {
			if (imagefile.getSize() > 0) {
				String image = storageService.uploadFile(imagefile);
				userDTO.setAvatar(image);
			} 
			userDTO.setEnabled(true);
			userService.add(userDTO);
			return "redirect:/admin/user/search";
		}

	}

	@GetMapping(value = "/admin/user/edit")
	public String AdminEditUserGet(Model model, @RequestParam(value = "id") int id) {
		try {
			UserDTO userDTO = userService.get(id);
			System.out.println("Anh " + userDTO.getAvatar());
			System.out.println("Anh " + userDTO.getId());
			model.addAttribute("userDTO", userDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/user/EditUser";
	}

	@PostMapping(value = "/admin/user/edit")
	public String AdminEditUserPost(Model model, @ModelAttribute(value = "userDTO") UserDTO userDTO,
			@RequestParam(name = "file") MultipartFile imagefile) {
		System.out.println("role userDTO " + userDTO.getRole());
		if (imagefile.getSize() > 0) {
			String image = storageService.uploadFile(imagefile);
			userDTO.setAvatar(image);
		}
		userDTO.setEnabled(true);
		userService.update(userDTO);
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/delete")
	public String AdminDeleteUser(Model model, @RequestParam(value = "id") String id,
			@RequestParam(value = "page", required = false) int currentPage,
			@RequestParam(value = "name", required = false) String name) {
		userService.delete(Integer.valueOf(id));

		System.out.println("delete name:" + name);

		return "redirect:/admin/user/search?page=" + currentPage + "&name=" + name;
	}

	@GetMapping(value = "/admin/user/search")
	public String AdminSearchUserPost(HttpServletRequest request,
			@RequestParam(value = "name", required = false) String name) {
		System.out.println("name :" + name);
		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

		if (name != null && !name.equals("null")) {
			long count = userService.countSearch(name);
			double result = Math.ceil((double) count / 5);
			List<UserDTO> listUserDTOs = userService.search(name, (page - 1) * 5, 5);
			if (listUserDTOs.isEmpty()) {
				System.out.println("khong co ket qua");
			} else {
				System.out.println("co ket qua");
				request.setAttribute("result", result);
				request.setAttribute("name", name);
				request.setAttribute("listUserDTOs", listUserDTOs);
				request.setAttribute("currentPage", page);
			}
		} else {
			long count = userService.countGetAll();
			double result = Math.ceil((double) count / 5);
			List<UserDTO> listUserDTOs = userService.getAll((page - 1) * 5, 5);
			System.out.println("result :" + result);
			request.setAttribute("result", result);
			request.setAttribute("currentPage", page);
			request.setAttribute("listUserDTOs", listUserDTOs);
		}

		return "admin/user/SearchUser";
	}

}