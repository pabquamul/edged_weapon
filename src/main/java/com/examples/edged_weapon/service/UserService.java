package com.examples.edged_weapon.service;

import com.examples.edged_weapon.dto.CreateOrderRequestDto;
import com.examples.edged_weapon.dto.StashedProductDto;
import com.examples.edged_weapon.dto.UpdateUserRequestDto;
import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.EdgedWeapon;
import com.examples.edged_weapon.models.Order;
import com.examples.edged_weapon.models.StashedProducts;
import com.examples.edged_weapon.models.User;
import com.examples.edged_weapon.repo.OrderRepository;
import com.examples.edged_weapon.repo.ProductRepository;
import com.examples.edged_weapon.repo.StashedProductRepository;
import com.examples.edged_weapon.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserService  {

    private final UserRepository userRepo;

    private final ProductRepository productRepo;

    private final StashedProductRepository stashedProductRepo;

    private final OrderRepository orderRepo;

    private final ProductService productService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MailService mailService;

    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepo.findAll(pageable);
        return users;
    }

    public User findById(Long userId) {
        User result = userRepo.findById(userId).orElse(null);

        if (result == null) {
            throw new SpringEdgedWeaponException("нет пользователя с данным Id");
        }
        return result;
    }

    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            throw new SpringEdgedWeaponException("нет пользователя с данной почтой");
        }
        return user;
    }

    public void delete(Long id) {
        userRepo.deleteById(id);

        log.info("IN delete - user with id: {} successfully deleted", id);
    }



    @Transactional
    public Order createOrder(CreateOrderRequestDto order, Long userId) {
        User currentUser = findById(userId);

        Set<StashedProducts> orderProducts = new HashSet<>();

        BigDecimal price = new BigDecimal(0);

        for (StashedProductDto stashedProductDto: order.getStashedProductDto()) {
            EdgedWeapon product = productService.findById(stashedProductDto.getProductId());

            if (product.getQuantity() == 0 || product.getQuantity() < stashedProductDto.getAmountInStash()) {
                throw new SpringEdgedWeaponException("not enough goods in storage");
            }

            BigDecimal multiplier = new BigDecimal(stashedProductDto.getAmountInStash());
            BigDecimal multipliedPrice = product.getPrice().multiply(multiplier);

            price = price.add(multipliedPrice);

            StashedProducts stashedProducts = StashedProducts.builder()
                    .product(product)
                    .amountInStash(stashedProductDto.getAmountInStash())
                    .created(new Date())
                    .updated(new Date())
                    .build();

            Integer newProductAmount = product.getQuantity() - stashedProductDto.getAmountInStash();

            if (newProductAmount == 0) {
                stashedProductRepo.deleteCartItemsByProductId(product.getId());
            } else {
                List<StashedProducts> allStashedProducts = stashedProductRepo.findAllByProduct(product.getId());
                allStashedProducts.forEach(tempStashedProduct -> {
                    if (tempStashedProduct.getAmountInStash() > newProductAmount) {
                        tempStashedProduct.setAmountInStash(newProductAmount);
                        stashedProductRepo.save(tempStashedProduct);
                    }
                });
            }

            product.setQuantity(newProductAmount);
            product.setUpdated(new Date());

            productRepo.save(product);

            orderProducts.add(stashedProducts);
        }

        Order newOrder = Order.builder()
                .created(new Date())
                .updated(new Date())
                .description(order.getDescription())
                .price(price)
                .stashedProducts(orderProducts)
                .build();

        currentUser.addOrder(newOrder);

        User result = userRepo.save(currentUser);

        Order createdOrder = result
                .getOrders()
                .stream()
                .sorted((order1, order2) -> order2.getCreated().compareTo(order1.getCreated()))
                .findFirst()
                .get();

        log.info("IN createOrder - user with id: {} successfully created order: {}", userId, createdOrder);
        return createdOrder;
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        User currentUser = findById(userId);

        Order canceledOrder = orderRepo.findById(orderId).orElse(null);

        if (canceledOrder == null
                || canceledOrder.getUser().getId() != userId) {
            throw new SpringEdgedWeaponException("no order with this id");
        }

        for (StashedProducts stashedProduct: canceledOrder.getStashedProducts()) {
            EdgedWeapon product = productService.findById(stashedProduct.getProduct().getId());

            product.setQuantity(stashedProduct.getAmountInStash() + product.getQuantity());
            product.setUpdated(new Date());

            productRepo.save(product);
        }
        User result = userRepo.save(currentUser);

        log.info("IN cancelOrder - user with id: {} successfully canceled order: {}", userId, canceledOrder);
    }

    public User update(UpdateUserRequestDto updateUserRequestDto, Long id){
        User currentUser = findById(id);

        currentUser.setUsername(updateUserRequestDto.getUsername());
        currentUser.setSurname(updateUserRequestDto.getSurname());
        currentUser.setEmail(updateUserRequestDto.getEmail());
        currentUser.setUpdated(new Date());

        String newPassword = updateUserRequestDto.getNewPassword();
        if (newPassword != null && newPassword.length() != 0){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
        }
        User res = userRepo.save(currentUser);
        return res;
    }


}