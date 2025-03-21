package com.example.repo_midterm.Service.Impl;

import com.example.repo_midterm.DTO.Response.PhoneResponseDTO;
import com.example.repo_midterm.Entity.Phone;
import com.example.repo_midterm.Repository.IPhoneRepository;
import com.example.repo_midterm.Service.IPhoneService;
import com.example.repo_midterm.convert.PhoneConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Trần Anh Thư 22110431

@Service
public class PhoneServiceImpl implements IPhoneService {
    @Autowired
    IPhoneRepository iphoneRepository;
    @Autowired
    PhoneConvert phoneConvert;

    @Override
    public List<PhoneResponseDTO> getLastPhone() {

        List<Phone> phones = iphoneRepository.findAllByStatusTrueOrderByCreateDateDesc();

        List<PhoneResponseDTO> phonesResponseDTOs = new ArrayList<>();
        Map<String, Phone> groupPhones = new HashMap<>();

        for (Phone phone : phones) {
            if (!groupPhones.containsKey(phone.getPhoneName())) {
                groupPhones.put(phone.getPhoneName(), phone);
                PhoneResponseDTO phoneResponseDTO = phoneConvert.toPhoneResponseDTO(phone);
                phonesResponseDTOs.add(phoneResponseDTO);
            }
        }

        // Giới hạn chỉ lấy 6 sản phẩm mới nhất
//        return phonesResponseDTOs.size() > 6 ? phonesResponseDTOs.subList(0, 6) : phonesResponseDTOs;
        return phonesResponseDTOs;
    }

    // Vuong Duc Thoai 22110430
    public List<Phone> searchProducts(String keyword) {
        return iphoneRepository.searchPhones(keyword);
    }
}
