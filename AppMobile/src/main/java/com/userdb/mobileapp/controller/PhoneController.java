package com.userdb.mobileapp.controller;

import com.userdb.mobileapp.dto.responseDTO.PhoneResponseDTO;
import com.userdb.mobileapp.entity.Phone;
import com.userdb.mobileapp.service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PhoneController {
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
}
