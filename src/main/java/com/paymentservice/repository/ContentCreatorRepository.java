package com.paymentservice.repository;

import com.paymentservice.entity.ContentCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentCreatorRepository extends JpaRepository<ContentCreator, Long> {

    @Query("SELECT c FROM ContentCreator c WHERE c.email = :email or c.phoneNumber = :phoneNumber")
    ContentCreator findByEmailOrPhone(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    @Query("SELECT c FROM ContentCreator c WHERE c.isActive = true ")
    List<ContentCreator> findAllActiveContentCreators();

    @Query("SELECT c FROM ContentCreator c WHERE c.id = :id and c.isActive = true")
    Optional<ContentCreator> findActiveById(@Param("id") Long id);

}
