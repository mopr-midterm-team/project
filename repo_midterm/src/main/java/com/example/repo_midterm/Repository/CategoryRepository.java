package com.example.repo_midterm.Repository;


import com.example.repo_midterm.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Implement custom method:

}
