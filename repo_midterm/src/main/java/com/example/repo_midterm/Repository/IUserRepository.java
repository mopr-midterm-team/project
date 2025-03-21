package com.example.repo_midterm.Repository;

import com.example.repo_midterm.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
