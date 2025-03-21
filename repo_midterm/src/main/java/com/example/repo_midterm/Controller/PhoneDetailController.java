package com.example.repo_midterm.Controller;

import com.example.repo_midterm.DTO.Response.PhoneResponseDTO;
import com.example.repo_midterm.Entity.Phone;
import com.example.repo_midterm.Service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//Trần Anh Thư 22110431

@RestController
@RequestMapping("/api")
public class PhoneDetailController {
    @Autowired
    IPhoneService phoneService;
    @GetMapping("/phones")
    public List<PhoneResponseDTO> getLastPhones() {
        return phoneService.getLastPhone();
    }

    @GetMapping("/detail")
    public ResponseEntity<List<Phone>> getPhonesByCategory(@RequestParam Long PhoneID) {
//        List<Phone> phones = phoneService.getPhonesByCategory(categoryID);
//        return ResponseEntity.ok(phones);
        return null;
    }

   // Vuong Duc Thoai 22110430
    @GetMapping("/search")
    public List<Phone> searchPhones(@RequestParam String keyword) {
        return phoneService.searchProducts(keyword);
    }
}
