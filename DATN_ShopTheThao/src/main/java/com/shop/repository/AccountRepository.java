package com.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shop.entity.Account;
import com.shop.entity.Product;

public interface AccountRepository  extends JpaRepository<Account, String>{
	@Query("Select o from Account o where o.email = ?1")
	Optional<Account> findAccountByEmail(String email);
}
