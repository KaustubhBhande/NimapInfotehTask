package com.onetomany.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onetomany.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findAll(Pageable pageble);
}
