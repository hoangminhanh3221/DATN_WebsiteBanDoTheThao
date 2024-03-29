package com.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shop.entity.Account;

public interface AccountService {
	
	 Page<Account> findAllAccount(Pageable pageable);
	
	List<Account> findAllAccount();

	Optional<Account> findAccountById(String accountId);
	
	Account createAccount(Account account);

	Account updateAccount(Account account);

	void deleteAccount(String accountId);
	
	Optional<Account> findByEmail(String email);
	
}
