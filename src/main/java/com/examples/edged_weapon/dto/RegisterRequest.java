package com.examples.edged_weapon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    private String username;
    private String surname;
    private String email;
    private String password;

}
