package com.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmployeeId")
	private Integer employeeId;
	
	@Column(name = "EmployeeName", columnDefinition = "nvarchar(50)", nullable = true)
	private String employeeName;
	
	@Column(name = "Gender", nullable = true)
	private Boolean gender;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Birthday", nullable = true)
	private Date birthday;
	
	@Transient
    private String birthdayString;
	
	@Column(name = "EmployeeImage", columnDefinition = "varchar(50)", nullable = true)
	private String employeeImage;
	
	@Column(name = "PhoneNumber", columnDefinition = "varchar(15)", nullable = true)
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "Username", referencedColumnName = "Username", nullable = true)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "AddressId", referencedColumnName = "AddressId", nullable = true)
	private Address address;
}
