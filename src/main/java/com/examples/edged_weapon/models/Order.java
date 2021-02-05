package com.examples.edged_weapon.models;

import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "orders")
public class Order extends BaseEntity{


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @JsonView(SecurityViews.User.class)
    private Set<StashedProducts> stashedProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    @JsonView(SecurityViews.User.class)
    private User user;

    @DecimalMin(value = "0.0", inclusive = false, message = "Цена обязательна")
    @JsonView(SecurityViews.Anonymous.class)
    private BigDecimal price;

    @JsonView(SecurityViews.User.class)
    private String arrivalPoint;

    @JsonView(SecurityViews.User.class)
    private String description;

//    @OneToMany(mappedBy = "order")
//    private Set<StashedProducts> stashedProducts;

}
