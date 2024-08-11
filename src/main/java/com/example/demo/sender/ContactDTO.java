package com.example.demo.sender;

import lombok.Data;

@Data
public class ContactDTO {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String company;
    private String email;
}
