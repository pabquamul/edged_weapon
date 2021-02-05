package com.examples.edged_weapon.models;

import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EdgedWeapon extends BaseEntity {

    @NotBlank(message = "название обязательно")
    @JsonView(SecurityViews.Anonymous.class)
    private String name;
    @NotBlank(message = "название брэнда обязательно")
    @JsonView(SecurityViews.Anonymous.class)
    private String brand;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer bladeLength;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer overallLength;
    @JsonView(SecurityViews.Anonymous.class)
    @NotNull(message = "вес обязателен")
    private Integer weight;
    @JsonView(SecurityViews.Anonymous.class)
    private String bladeSteel;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer handleLength;
    @JsonView(SecurityViews.Anonymous.class)
    private String handleMaterial;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer backThickness;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer hardnessOfSteel;
    @JsonView(SecurityViews.Anonymous.class)
    @NotNull(message = "цена обязательна")
    private BigDecimal price;
    @JsonView(SecurityViews.Anonymous.class)
    private String additionalFeatures;
    @JsonView(SecurityViews.Anonymous.class)
    private String description;
    @JsonView(SecurityViews.Anonymous.class)
    private Integer quantity;
    @JsonView(SecurityViews.Anonymous.class)
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "product_types",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "types_id"))
    @JsonView(SecurityViews.Anonymous.class)
    private Set<Types> productTypes;

    @OneToMany (mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<StashedProducts> stashedProducts;




    public EdgedWeapon(String name){
        this.name = name;
    }


}


