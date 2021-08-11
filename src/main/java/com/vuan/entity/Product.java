package com.vuan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Transactional
@Data
@Table(name = "product")
@NoArgsConstructor
@RequiredArgsConstructor
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private int id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category ;
}
