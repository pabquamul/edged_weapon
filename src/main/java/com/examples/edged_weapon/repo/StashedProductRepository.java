package com.examples.edged_weapon.repo;


import com.examples.edged_weapon.models.StashedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StashedProductRepository extends JpaRepository<StashedProducts,Long> {
    @Transactional
    @Modifying
    @Query("delete from StashedProducts where product_id = :id and order_id is null")
    void deleteCartItemsByProductId(Long id);

    @Query("select sp from StashedProducts sp where product_id = :id and order_id is null")
    List<StashedProducts> findAllByProduct(Long id);
}
