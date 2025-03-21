package com.example.repo_midterm.Repository;

import com.example.repo_midterm.Entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//Trần Anh Thư 22110431

public interface IPhoneRepository extends JpaRepository<Phone, Integer> {

    List<Phone>findAllByStatusTrueOrderByCreateDateDesc();

    // Vuong Duc Thoai 22110430
    @Query("SELECT p FROM Phone p WHERE " +
            "LOWER(p.phoneName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.color) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Phone> searchPhones(@Param("keyword") String keyword);
}