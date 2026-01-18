package com.example.fileManagment.demo.fileManagment.repository;

import com.example.fileManagment.demo.fileManagment.enam.Role;
import com.example.fileManagment.demo.fileManagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Object> findByEmail(String email);

    boolean existsByRole(Role role);
}
