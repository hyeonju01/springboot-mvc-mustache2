package com.example.bbs2.repository;

import com.example.bbs2.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
}