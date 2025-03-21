package com.userdb.mobileapp.convert;

import com.userdb.mobileapp.dto.responseDTO.PhoneResponseDTO;
import com.userdb.mobileapp.entity.Phone;
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
        if (phone.getImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(phone.getImage());
            phoneResponseDTO.setImagePhone(base64Image);
        }
        return phoneResponseDTO;
    }
}
