package com.vuan.service;

import java.util.List;

import com.vuan.model.UserAddressDTO;

public interface UserAddressService {
	void add(UserAddressDTO userAddressDTO);

	void update(UserAddressDTO userAddressDTO);

	void delete(int id);
	
	UserAddressDTO get(int id);

	UserAddressDTO getByUserId(int userId);

	List<UserAddressDTO> getAll();
}
