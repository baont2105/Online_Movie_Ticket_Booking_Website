package com.poly.demo.repository;

import com.poly.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByRole(String role);
}
