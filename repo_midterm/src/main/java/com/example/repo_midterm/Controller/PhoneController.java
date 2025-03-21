package com.example.repo_midterm.Controller;


import com.example.repo_midterm.Entity.Phone;
import com.example.repo_midterm.Service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/api/phonesH")
    public ResponseEntity<List<Phone>> getPhonesByCategory(@RequestParam Long categoryID) {
        List<Phone> phones = phoneService.getPhonesByCategory(categoryID);
        return ResponseEntity.ok(phones);
    }


}
