package com.vuan.service.Iplm;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.UserAddressDao;
import com.vuan.entity.User;
import com.vuan.entity.UserAddress;
import com.vuan.model.UserAddressDTO;
import com.vuan.model.UserDTO;
import com.vuan.model.UserPrincipal;
import com.vuan.service.UserAddressService;

@Service
@Transactional
public class UserAddressServiceIplm implements UserAddressService{
	@Autowired
	UserAddressDao userAddressDao;
	
	@Override
	public void add(UserAddressDTO userAddressDTO) {
		UserAddress userAddress=new UserAddress();
		User user=new User();
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		userAddress.setName(userAddressDTO.getName());
		userAddress.setAddress(userAddressDTO.getAddress());
		userAddress.setDistrict(userAddressDTO.getDistrict());
		userAddress.setPhoneNumber(userAddressDTO.getPhoneNumber());
		userAddress.setCity(userAddressDTO.getCity());

		user.setId(principal.getId());
		userAddress.setUser(user);;
		
		userAddressDao.add(userAddress);
	}
	
	@Override
	public void update(UserAddressDTO userAddressDTO) {
		UserAddress userAddress=userAddressDao.get(userAddressDTO.getId());
		User user=new User();
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(userAddress !=  null) {
			userAddress.setId(userAddressDTO.getId());
			userAddress.setName(userAddressDTO.getName());
			userAddress.setAddress(userAddressDTO.getAddress());
			userAddress.setDistrict(userAddressDTO.getDistrict());
			userAddress.setPhoneNumber(userAddressDTO.getPhoneNumber());
			userAddress.setCity(userAddressDTO.getCity());
			
			user.setId(principal.getId());
			userAddress.setUser(user);
			
			userAddressDao.update(userAddress);
		}
		
	}

	@Override
	public void delete(int id) {
		UserAddress userAddress=userAddressDao.get(id);
		if(userAddress != null) {
			userAddressDao.delete(id);
		}	
	}

	@Override
	public UserAddressDTO get(int id) {
		UserAddress userAddress=userAddressDao.get(id);
		return convert(userAddress);
	}
	
	@Override
	public UserAddressDTO getByUserId(int userId) {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserAddress userAddress = userAddressDao.get(principal.getId());
		return convert(userAddress);
	}

	@Override
	public List<UserAddressDTO> getAll() {
		List<UserAddress> listUserAddresss = userAddressDao.getAll();
		List<UserAddressDTO> listUserAddressDTOs = new ArrayList<UserAddressDTO>();
		for (UserAddress userAddress : listUserAddresss) {
			listUserAddressDTOs.add(convert(userAddress));
		}
		return listUserAddressDTOs;
	}
	
	private UserAddressDTO convert(UserAddress userAddress) {
		UserAddressDTO userAddressDTO = new UserAddressDTO();
		UserDTO userDTO=new UserDTO();
		
		userAddressDTO.setId(userAddress.getId());
		userAddressDTO.setAddress(userAddress.getAddress());
		userAddressDTO.setCity(userAddress.getCity());
		userAddressDTO.setDistrict(userAddress.getDistrict());
		userAddressDTO.setName(userAddress.getName());
		userAddressDTO.setPhoneNumber(userAddress.getPhoneNumber());
		
		userDTO.setId(userAddress.getUser().getId());
		userAddressDTO.setUserDTO(userDTO);
		
		return userAddressDTO;
	}

}
