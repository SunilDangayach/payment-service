package com.discover.dpay.payment_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discover.dpay.payment_service.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public Account findOneByAccountNumber(String accountNumber);

}
