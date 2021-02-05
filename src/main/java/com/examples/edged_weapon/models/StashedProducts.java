package com.examples.edged_weapon.models;

import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StashedProducts extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonView(SecurityViews.User.class)
    private EdgedWeapon product;

//    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    @JsonView(SecurityViews.User.class)
//    private User user;
//
//    @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn (name = "order_id")
//    @JsonView(SecurityViews.User.class)
//    private Order order;

    @JsonView(SecurityViews.User.class)
    private Integer amountInStash;

}
