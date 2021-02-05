package com.examples.edged_weapon.service;

import com.examples.edged_weapon.dto.JsonPage;
import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.EdgedWeapon;
import com.examples.edged_weapon.models.Types;
import com.examples.edged_weapon.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final TypesService typesService;



    public JsonPage<EdgedWeapon> findByFilters(
            String search,
            Iterable<String> types,
            Integer maxPrice,
            Integer minPrice,
            Pageable pageable) {
        BigDecimal maxPriceBigDecimal = maxPrice == null ? null : new BigDecimal(maxPrice);
        BigDecimal minPriceBigDecimal = minPrice == null ? null : new BigDecimal(minPrice);

        JsonPage<EdgedWeapon> result = new JsonPage(productRepository.findByFilters(search, types,
                maxPriceBigDecimal, minPriceBigDecimal, pageable), pageable);

        return result;
    }
    public EdgedWeapon findById(Long id) {
        EdgedWeapon result = productRepository.findById(id).orElse(null);

        if (result == null) {
                throw new SpringEdgedWeaponException("товара с данным ID не существует");
        }
        return result;
    }
    public Set<EdgedWeapon> findByIds(Iterable<Long> ids) {
        return new HashSet<>(productRepository.findAllById(ids));
    }
    public BigDecimal getMaxPrice() {
        BigDecimal maxPrice = productRepository.findByMaxPrice();

        if (maxPrice == null) {
            throw new SpringEdgedWeaponException("max price is not found");
        }
        return maxPrice;
    }

    public BigDecimal getMinPrice() {
        return productRepository.findByMinPrice();
    }

    public Map<String, Object> getFilters() {
        Set<Types> types = typesService.findAll();
        Double maxPrice = Math.ceil(getMaxPrice().doubleValue());
        Double minPrice = Math.floor(getMinPrice().doubleValue());;

        Map<String, Object> result = new HashMap<>();

        result.put("types", types);
        result.put("lowestPrice", minPrice);
        result.put("highestPrice", maxPrice);

        return result;
    }

    public EdgedWeapon create(EdgedWeapon product){
        product.setCreated(new Date());
        product.setUpdated(new Date());

        EdgedWeapon createdProduct = productRepository.save(product);
        if(createdProduct == null){
            throw new SpringEdgedWeaponException("creation product failed");
        }
        return createdProduct;

    }
    public EdgedWeapon update(EdgedWeapon product){
        Long productId = product.getId();

        if(productId == null){
            throw new SpringEdgedWeaponException("нет продукта с таким ID");
        }
        EdgedWeapon existedProduct = productRepository.findById(productId).orElse(null);

        if(existedProduct == null){
            throw new SpringEdgedWeaponException("update product not found");
        }
//        Integer currentProductAmount = product.getAmount();

        existedProduct.setBackThickness(product.getBackThickness());
        existedProduct.setBladeLength(product.getBladeLength());
        existedProduct.setBladeSteel(product.getBladeSteel());
        existedProduct.setHandleLength(product.getHandleLength());
        existedProduct.setHandleMaterial(product.getHandleMaterial());
        existedProduct.setHardnessOfSteel(product.getHardnessOfSteel());
        existedProduct.setName(product.getName());
        existedProduct.setOverallLength(product.getOverallLength());
        existedProduct.setWeight(product.getWeight());
        existedProduct.setAdditionalFeatures(product.getAdditionalFeatures());
        existedProduct.setBrand(product.getBrand());
        existedProduct.setDescription(product.getDescription());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setQuantity(product.getQuantity());
        existedProduct.setUpdated(new Date());

        EdgedWeapon updatedProduct = productRepository.save(product);

        if (updatedProduct == null){
            throw new SpringEdgedWeaponException("ошибка в обновлении товара");
        }

        return updatedProduct;
    }

    public void delete(EdgedWeapon product){
        Long productId = product.getId();
        if (productId == null){
            throw new SpringEdgedWeaponException("ошибка при удалении продукта");
        }
        productRepository.deleteById(productId);
    }



}
