package com.discover.dpay.payment_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discover.dpay.payment_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
