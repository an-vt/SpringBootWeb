package com.vuan.vadilator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.vuan.model.UserDTO;

@Component
public class UserDTOVadilator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate( Object target, Errors errors) {
		UserDTO userDTO = (UserDTO) target;
		
		if(userDTO.getName() == null || userDTO.getName().length() == 0) {
			System.out.println("truong hop name null");
			errors.rejectValue("name", "field.required");
		
		}
		
//		ValidationUtils.rejectIfEmpty(errors, "password", "field.required");
		
		if(userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 12) {
			errors.rejectValue("password", "password.invalid");
		}
		
	}

}
