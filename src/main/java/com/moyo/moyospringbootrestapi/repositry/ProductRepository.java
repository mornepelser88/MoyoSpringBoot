package com.moyo.moyospringbootrestapi.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moyo.moyospringbootrestapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
