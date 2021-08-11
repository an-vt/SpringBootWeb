package com.vuan.service.Iplm;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.vuan.dao.UserDao;
import com.vuan.entity.User;
import com.vuan.model.UserDTO;
import com.vuan.model.UserPrincipal;
import com.vuan.service.UserService;
import com.vuan.utils.PasswordGenerator;

@Service
@Transactional
public class UserServiceIplm implements UserService,UserDetailsService {
	@Autowired
	UserDao userDao;
	
	@Override
	public void add(UserDTO userDTO) {
		User user=new User();
		user.setName(userDTO.getName());
		user.setAge(userDTO.getAge());
		user.setEnabled(userDTO.getEnabled());
		user.setRole(userDTO.getRole());
		user.setUsername(userDTO.getUsername());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()) );
		user.setGender(userDTO.getGender());
		user.setAddress(userDTO.getAddress());
		user.setPhone(userDTO.getPhone());
		user.setEmail(userDTO.getEmail());
		user.setAvatar(userDTO.getAvatar());
		
		userDao.add(user);
	}

	@Override
	public void update(UserDTO userDTO) {
		User user = userDao.get(userDTO.getId());
		if(user != null) {
			user.setId(userDTO.getId());
			user.setName(userDTO.getName());
			user.setAge(userDTO.getAge());
			user.setEnabled(userDTO.getEnabled());
			user.setRole(userDTO.getRole());
			user.setUsername(userDTO.getUsername());
			user.setPassword((userDTO.getPassword()));
			user.setGender(userDTO.getGender());
			user.setAddress(userDTO.getAddress());
			user.setPhone(userDTO.getPhone());
			user.setEmail(userDTO.getEmail());
			user.setAvatar(userDTO.getAvatar());
			
			userDao.update(user);
		}
	}

	@Override
	public void delete(int id) {
		User user = userDao.get(id);
		if(user != null) {
			userDao.delete(id);
		}	
	}

	@Override
	public UserDTO get(int id) {
		User user = userDao.get(id);
		return convert(user);
	}

	@Override
	public UserDTO getByUserName(String username) {
		User user = userDao.getByUserName(username);
		return convert(user);
	}

	@Override
	public List<UserDTO> search(String findName ,int start ,int length) {
		List<User> listUsers = userDao.search(findName ,start ,length);
		List<UserDTO> listUserDTOs = new ArrayList<UserDTO>();
		for (User user : listUsers) {
			listUserDTOs.add(convert(user));
		}
		return listUserDTOs;
	}

	@Override
	public List<UserDTO> getAll(int start ,int length) {
		List<User> listUsers = userDao.getAll(start ,length);
		List<UserDTO> listUserDTOs = new ArrayList<UserDTO>();
		for (User user : listUsers) {
			listUserDTOs.add(convert(user));
		}
		return listUserDTOs;
	}
	
	private UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setAge(user.getAge());
		userDTO.setRole(user.getRole());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setGender(user.getGender());
		userDTO.setAddress(user.getAddress());
		userDTO.setEnabled(user.getEnabled());
		userDTO.setPhone(user.getPhone());
		userDTO.setEmail(user.getEmail());
		userDTO.setAvatar(user.getAvatar());
		return userDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("khong co user");
		}
		System.out.println("co user");
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		
		UserPrincipal userPrincipal = new UserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(), true,
				true, true, authorities);
		
		userPrincipal.setId(user.getId());
		userPrincipal.setUsername(user.getName());
		userPrincipal.setRole(user.getRole());
		userPrincipal.setPhone(user.getPhone());
		userPrincipal.setEmail(user.getEmail());
		userPrincipal.setAvatar(user.getAvatar());
		
		return userPrincipal;
	}

	@Override
	public long countSearch(String name) {
		long count=userDao.countSearch(name);
		return count;
	}

	@Override
	public long countGetAll() {
		long count=userDao.countGetAll();
		return count;
	}
}
