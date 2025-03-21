package com.userdb.mobileapp.service.impl;

import com.userdb.mobileapp.convert.PhoneConvert;
import com.userdb.mobileapp.dto.responseDTO.PhoneResponseDTO;
import com.userdb.mobileapp.entity.Phone;
import com.userdb.mobileapp.repository.IPhoneRepository;
import com.userdb.mobileapp.repository.custom.IPhoneRepositoryCustom;
import com.userdb.mobileapp.service.IPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhoneServiceImpl implements IPhoneService {
    @Autowired
    IPhoneRepository iphoneRepository;
    @Autowired
    PhoneConvert phoneConvert;
    @Autowired
    IPhoneRepositoryCustom phoneRepositoryCustom;

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
}
