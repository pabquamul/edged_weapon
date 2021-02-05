package com.examples.edged_weapon.repo;

import com.examples.edged_weapon.models.EdgedWeapon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<EdgedWeapon, Long> {



    @Query("select distinct ew from EdgedWeapon ew left join ew.productTypes types " +
            "where :name is null or upper(ew.name) like '%'  " +
            "and (:maxPrice is null or ew.price <= :maxPrice) " +
            "and (:minPrice is null or ew.price >= :minPrice) " +
            "and ((:typeNames) is null or types.name in (:typeNames))")
    Page<EdgedWeapon> findByFilters(String name, Iterable<String> typeNames, BigDecimal maxPrice, BigDecimal minPrice, Pageable pageable);

//    @Query("select distinct ew from EdgedWeapon ew left join ew.productTypes types where (:name is null or upper(ew.name) like '%' || upper(text(:name)) || '%'")
//    Page<EdgedWeapon> findByFilters(String name, Iterable<String> typeNames, BigDecimal maxPrice, BigDecimal minPrice, Pageable pageable);

    List<EdgedWeapon> findAllById(Iterable<Long> ids);

    @Query("select max(price) from EdgedWeapon")
    BigDecimal findByMaxPrice();

    @Query("select min(price) from EdgedWeapon")
    BigDecimal findByMinPrice();

    Page<EdgedWeapon> findAll(Pageable pageable);

}
