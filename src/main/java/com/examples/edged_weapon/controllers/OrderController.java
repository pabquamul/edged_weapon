package com.examples.edged_weapon.controllers;

import com.examples.edged_weapon.dto.JsonPage;
import com.examples.edged_weapon.models.Order;
import com.examples.edged_weapon.models.User;
import com.examples.edged_weapon.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Secured("ROLE_ANONYMOUS")
@RequestMapping("api/view/admin/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/pageable")
    public ResponseEntity getAllOrdersPageable(@PageableDefault(size = 30, sort = { "updated" }, direction = Sort.Direction.DESC) Pageable pageable) {
        JsonPage<Order> orders = new JsonPage(orderService.findAll(pageable), pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getOrder(@PathVariable(name = "id", required = true) Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("{id}/user")
    public ResponseEntity getOrderUser(@PathVariable(name = "id", required = true) Long id) {
        User user = orderService.findUserByOrder(id);
        return ResponseEntity.ok(user);
    }
}
