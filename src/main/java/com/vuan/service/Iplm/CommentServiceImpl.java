package com.vuan.service.Iplm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuan.dao.CommentDao;
import com.vuan.entity.Comment;
import com.vuan.entity.Product;
import com.vuan.entity.User;
import com.vuan.model.CommentDTO;
import com.vuan.model.ProductDTO;
import com.vuan.model.UserDTO;
import com.vuan.service.CommentService;
import com.vuan.utils.DateTimeUtils;



@Transactional
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentDao commentDao;

	@Override
	public void add(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		Product product= new Product();
		product.setId(commentDTO.getProductDTO().getId());
		comment.setProduct(product);
		
		User user = new User();
		user.setId(commentDTO.getUserDTO().getId());
//		user.setName(commentDTO.getUserDTO().getName());
		comment.setUser(user);
		comment.setCreatedDate(new Date());
		commentDao.add(comment);
	}

	@Override
	public void update(CommentDTO commentDTO) {
		Comment comment = commentDao.get(commentDTO.getId());
		if (comment != null) {
			comment.setContent(commentDTO.getContent());
			commentDao.update(comment);
		}

	}

	@Override
	public void delete(int id) {
		Comment comment = commentDao.get(id);
		if (comment != null) {
			commentDao.delete(id);
		}
	}

	@Override
	public CommentDTO get(int id) {
		Comment comment = commentDao.get(id);
		return convert(comment);
	}

	@Override
	public List<CommentDTO> searchByProduct(int id) {
		List<Comment> listComments= commentDao.searchByProduct(id);
		List<CommentDTO> commentDTOs= new ArrayList<>();
		for(Comment comment : listComments) {
			CommentDTO commentDTO= new CommentDTO();
			commentDTO.setContent(comment.getContent());
			commentDTO.setCreatedDate(String.valueOf(comment.getCreatedDate()));
			
			ProductDTO productDTO= new ProductDTO();
			productDTO.setId(comment.getProduct().getId());
			commentDTO.setProductDTO(productDTO);
			
			UserDTO userDTO = new UserDTO();
			userDTO.setName(comment.getUser().getName());
			userDTO.setAvatar(comment.getUser().getAvatar());
			commentDTO.setUserDTO(userDTO);
			
			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}
	
	public CommentDTO convert(Comment comment) {
		CommentDTO commentDTO=new CommentDTO();
		commentDTO.setId(comment.getId());
		commentDTO.setContent(comment.getContent());
		commentDTO.setCreatedDate(DateTimeUtils.formatDate(comment.getCreatedDate(), DateTimeUtils.DD_MM_YYYY_HH_MM));
		
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(comment.getProduct().getId());
		commentDTO.setProductDTO(productDTO);
		
		UserDTO userDTO=new UserDTO();
		userDTO.setId(comment.getUser().getId());
		userDTO.setAvatar(comment.getUser().getAvatar());
		commentDTO.setUserDTO(userDTO);
		return commentDTO;
	}
}

