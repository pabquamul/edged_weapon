package com.examples.edged_weapon.controllers;

import com.examples.edged_weapon.models.Types;
import com.examples.edged_weapon.service.TypesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@AllArgsConstructor
@Secured("ROLE_ANONYMOUS")
@RequestMapping(value = "/api/view/admin/productTypes")
public class TypesController {

    private final TypesService typesService;

    @GetMapping("")
    public ResponseEntity getAllProductTypes() {
        Set<Types> result = typesService.findAll();
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity createProductType(
            @Valid @RequestBody Types productType
    ) {
        Types result = typesService.create(productType);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("")
    public ResponseEntity updateProductType(
            @Valid @RequestBody Types productType
    ) {
        Types result = typesService.update(productType);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProductType(@PathVariable Long id) {
        typesService.delete(id);

        return ResponseEntity.ok(null);
    }
}

