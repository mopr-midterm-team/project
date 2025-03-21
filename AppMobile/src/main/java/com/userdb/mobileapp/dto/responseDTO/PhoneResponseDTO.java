package com.userdb.mobileapp.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneResponseDTO {
    private int phoneID;
    private String phoneName;
    private double price;
    private String color;
    private boolean status;
    private String description;
    private String imagePhone;
}
