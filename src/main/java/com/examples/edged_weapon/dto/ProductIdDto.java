package com.examples.edged_weapon.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductIdDto {
    @NotBlank
    private Long id;
}
