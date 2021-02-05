package com.examples.edged_weapon.controllers;

import com.examples.edged_weapon.models.EdgedWeapon;
import com.examples.edged_weapon.repo.ProductRepository;
import com.examples.edged_weapon.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("api/view")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity getProducts(
            @RequestParam(value="type", required=false) Set<String> types,
            @RequestParam(value="search", required = false) String search,
            @RequestParam(value="maxPrice", required = false) Integer maxPrice,
            @RequestParam(value="minPrice", required = false) Integer minPrice,
            @RequestParam(value="id", required=false) Set<Long> ids,
            @PageableDefault(size = 20, sort = { "created" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (ids == null) {
            return ResponseEntity.ok(productService.findByFilters(search, types, maxPrice, minPrice, pageable));
        }
        return ResponseEntity.ok(productService.findByIds(ids));
    }

    @GetMapping("/products/filters")
    public ResponseEntity getFilters() {
        Map<String, Object> result = productService.getFilters();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        EdgedWeapon result = productService.findById(id);
        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/products")
    public ResponseEntity createProduct(
            @Valid @RequestBody EdgedWeapon product
    ) {
        EdgedWeapon result = productService.create(product);

        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/admin/products")
    public ResponseEntity updateProduct(
            @Valid @RequestBody EdgedWeapon product
    ) {
        EdgedWeapon result = productService.update(product);

        return ResponseEntity.ok(result);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/products")
    public ResponseEntity deleteProduct(
            @Valid @RequestBody EdgedWeapon product
    ) {
        // productService.delete(product); TODO rethink the logic

        return ResponseEntity.ok(null);
    }
}

