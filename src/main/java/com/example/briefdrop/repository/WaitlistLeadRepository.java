package com.example.briefdrop.repository;

import com.example.briefdrop.entity.WaitlistLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitlistLeadRepository extends JpaRepository<WaitlistLead, Long> {

    boolean existsByEmail(String email);
}
