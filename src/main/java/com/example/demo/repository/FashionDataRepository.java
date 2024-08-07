
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.FashionData;

public interface FashionDataRepository extends JpaRepository<FashionData, Long> {
}
