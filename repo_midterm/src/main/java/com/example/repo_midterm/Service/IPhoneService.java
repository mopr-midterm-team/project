package com.example.repo_midterm.Service;

import com.example.repo_midterm.DTO.Response.PhoneResponseDTO;
import com.example.repo_midterm.Entity.Phone;

import java.util.List;
//Trần Anh Thư 22110431

public interface IPhoneService {

    List<PhoneResponseDTO> getLastPhone();

    // Vuong Duc Thoai 22110430
    List<Phone> searchProducts(String keyword);
}
