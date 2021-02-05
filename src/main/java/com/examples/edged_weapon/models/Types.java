package com.examples.edged_weapon.models;

import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "types")
@Data
@AllArgsConstructor
public class Types extends BaseEntity{

    @Column(name = "name")
    @NotBlank(message = "Название обязательно")
    @JsonView(SecurityViews.Anonymous.class)
    String name;

    @ManyToMany (fetch = FetchType.LAZY, mappedBy = "productTypes")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    Set<EdgedWeapon> products;

}
