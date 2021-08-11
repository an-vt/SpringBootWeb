package com.vuan.service;

import java.util.List;

import com.vuan.model.UserDTO;

public interface UserService {
	void add(UserDTO userDTO);

	void update(UserDTO userDTO);

	void delete(int id);

	UserDTO get(int id);

	UserDTO getByUserName(String username);

	List<UserDTO> search(String findName ,int start ,int length);

	List<UserDTO> getAll(int start ,int length);
	
	long countSearch(String name);
	
	long countGetAll();
}
