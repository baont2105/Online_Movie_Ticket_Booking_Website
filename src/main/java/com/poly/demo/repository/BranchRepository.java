package com.poly.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.demo.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
	Optional<Branch> findByName(String name);

	Page<Branch> findAll(Pageable pageable);

	List<Branch> findByCity(String city);
}