package com.example.repo_midterm.Repository;


import com.example.repo_midterm.Entity.Category;
import com.example.repo_midterm.Entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    List<Phone> findByCategory(Category category);

}

