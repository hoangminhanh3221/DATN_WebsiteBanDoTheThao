package com.shop.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUBCATEGORY")
public class Subcategory implements Serializable{
	
	@Id
	@Column(name = "SubcategoryId", columnDefinition = "varchar(10)")
	private String subcategoryId;
	
	@Column(name = "SubcategoryName", columnDefinition = "nvarchar(50)", nullable = true)
	private String subcategoryName;
	
	@ManyToOne
	@JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId", nullable = true)
	private Category category;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
	private List<Product> products;
}
