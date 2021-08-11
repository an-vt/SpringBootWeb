package com.vuan.service;

import java.util.List;

import com.vuan.model.CommentDTO;

public interface CommentService {
	
	void add(CommentDTO commentDTO);

	void update(CommentDTO commentDTO);

	void delete(int id);

	CommentDTO get(int id);

	List<CommentDTO> searchByProduct(int id);
}
