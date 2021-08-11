package com.vuan.model;

import lombok.Data;

@Data
public class UserAddressDTO {
	private int id;
	private String name;
	private String phoneNumber;
	private String city;
	private String district;
	private String address;
	private UserDTO userDTO;
}
