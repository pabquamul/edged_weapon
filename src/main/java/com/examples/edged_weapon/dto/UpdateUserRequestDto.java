package com.examples.edged_weapon.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserRequestDto {

    @NotBlank(message = "required field")
    private String username;

    @NotBlank(message = "required field")
    private String surname;

    @Email(message = "email is not correct!")
    @NotBlank(message = "required field")
    private String email;


    private String newPassword;




}
