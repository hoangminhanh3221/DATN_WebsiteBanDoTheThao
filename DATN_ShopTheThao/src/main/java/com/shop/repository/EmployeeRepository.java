package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Customer;
import com.shop.entity.Employee;
import com.shop.entity.Product;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer>{
	@Query("SELECT c FROM Employee c WHERE c.account.username = :username")
	List<Employee> findEmployeeByUsername(String username);
}
