package com.vuan.vadilator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.vuan.model.CategoryDTO;
import com.vuan.model.UserDTO;

@Component
public class CategoryDTOVadilator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CategoryDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CategoryDTO categoryDTO = (CategoryDTO) target;

//		if (categoryDTO.getName() == null || categoryDTO.getName().length() == 0) {
//			System.out.println("truong hop name null");
//			errors.rejectValue("name", "field.required");
//		}

		ValidationUtils.rejectIfEmpty(errors, "name", "field.required");
//		
//		if(userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 12) {
//			errors.rejectValue("password", "password.invalid");
//		}

	}

}
