package com.examples.edged_weapon.service;


import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.Order;
import com.examples.edged_weapon.models.User;
import com.examples.edged_weapon.repo.OrderRepository;
import com.examples.edged_weapon.repo.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepo;

    private ProductService productService;

    private ProductRepository productRepo;

    public Page<Order> findAll(Pageable pageable) {
        Page<Order> result = orderRepo.findAll(pageable);
        return result;
    }

    public List<Order> findAll() {
        List<Order> result = orderRepo.findAll();

        return result;
    }

    public Order findById(Long id) {
        Order result = orderRepo.findById(id).orElse(null);

        if (result == null) {
            throw new SpringEdgedWeaponException("order not found");
        }
        return result;
    }
//
//    @Transactional
//    public Order updateOrderStatus(OrderStatus status, Long orderId) {
//        Order targetOrder = orderRepo.findById(orderId).orElse(null);
//
//        if (targetOrder == null) {
//            log.warn("IN updateOrderStatus - no order found by id: {}", orderId);
//            throw new ResourceNotFoundException();
//        }
//        log.info("IN updateOrderStatus - order {} found by id: {}", targetOrder, orderId);
//
//        if (status == OrderStatus.CANCELED) {
//            for (StashedProduct stashedProduct: targetOrder.getStashedProducts()) {
//                Product product = productService.findById(stashedProduct.getProduct().getId());
//
//                product.setAmount(stashedProduct.getAmountInStash() + product.getAmount());
//                product.setUpdated(new Date());
//
//                productRepo.save(product);
//            }
//        } else if (status == OrderStatus.COMPLETED) {
//            targetOrder.setCompletionDate(new Date());
//        }
//
//        targetOrder.setOrderStatus(status);
//        Order result = orderRepo.save(targetOrder);
//
//        if (result == null) {
//            log.info("IN updateOrderStatus - order with id: {} update failed", orderId);
//            throw new InternalServerErrorException();
//        }
//
//        log.info("IN updateOrderStatus - order with id: {} successfully updated", orderId);
//
//        return result;
//    }

    public User findUserByOrder(Long orderId) {
        Order targetOrder = orderRepo.findById(orderId).orElse(null);

        if (targetOrder == null) {
            throw new SpringEdgedWeaponException("user with this id was not found");
        }

        return targetOrder.getUser();
    }
}
