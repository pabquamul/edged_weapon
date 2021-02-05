package com.examples.edged_weapon.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class CreateOrderRequestDto {

    @NotEmpty
    private Set<StashedProductDto> stashedProductDto;

    private String description;

}