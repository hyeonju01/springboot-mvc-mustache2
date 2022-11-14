package com.example.bbs2.repository;

import com.example.bbs2.domain.Hospital;
import com.example.bbs2.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}