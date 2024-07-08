package com.example.jwt_demo.repo;

import com.example.jwt_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
