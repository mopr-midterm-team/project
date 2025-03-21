package com.userdb.mobileapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phoneID;

    private String phoneName;
    private double price;

    @Lob
    private byte[] image;

    private String color;
    private boolean status;
    private String description;
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = true)
    private Category category;
}
