package com.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentId")
	private Integer paymentId;
	
	@Column(name = "PaymentMethod", columnDefinition = "nvarchar(50)", nullable = true)
	private String paymentMethod;
	
	@Column(name = "PaymentAmount", nullable = true)
	private Double paymentAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PaymentDate", nullable = true)
	private Date paymentDate;
	 
	@Column(name = "PaymentStatus", nullable = true)
	private Boolean paymentStatus;
	
	@JsonIgnore
	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
	private List<OrderInfo> orderInfos;
}
