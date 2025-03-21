package com.example.repo_midterm.DTO.Response;

import lombok.Getter;
import lombok.Setter;
//Trần Anh Thư 22110431

@Getter
@Setter
public class PhoneResponseDTO {
    private int phoneID;
    private String phoneName;
    private double price;
    private String color;
    private boolean status;
    private String description;
    private String image;
}
