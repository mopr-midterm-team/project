package com.userdb.mobileapp.service;

import com.userdb.mobileapp.dto.responseDTO.PhoneResponseDTO;

import java.util.List;

public interface IPhoneService {

    List<PhoneResponseDTO> getLastPhone();
}
