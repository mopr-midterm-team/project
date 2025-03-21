package com.example.repo_midterm.convert;

import com.example.repo_midterm.DTO.Response.PhoneResponseDTO;
import com.example.repo_midterm.Entity.Phone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class PhoneConvert {
    @Autowired
    ModelMapper modelMapper;

    public PhoneResponseDTO toPhoneResponseDTO(Phone phone) {
        PhoneResponseDTO phoneResponseDTO = modelMapper.map(phone, PhoneResponseDTO.class);
        return phoneResponseDTO;
    }
}
