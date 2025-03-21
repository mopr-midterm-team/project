package com.userdb.mobileapp.repository;

import com.userdb.mobileapp.entity.Phone;
import com.userdb.mobileapp.repository.custom.IPhoneRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IPhoneRepository extends JpaRepository<Phone, Integer> {

    List<Phone>findAllByStatusTrueOrderByCreateDateDesc();
}