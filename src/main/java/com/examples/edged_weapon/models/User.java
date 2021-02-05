package com.examples.edged_weapon.models;

import com.examples.edged_weapon.dto.StashedProductDto;
import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;


@Entity
@Table(name = "usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

    @NotBlank(message = "Имя обязательно!")
    @Size(max = 30, message = "имя не более 30 символов")
    @JsonView(SecurityViews.User.class)
    private String username;

    @NotBlank(message = "фамилия обязательна!")
    @JsonView(SecurityViews.User.class)
    private String surname;

    @Email(message = "некорректный email!")
    @NotBlank(message = "почта обязательна!")
    @JsonView(SecurityViews.User.class)
    private String email;

    @NotBlank(message = "Пароль обязателен!")
    @JsonIgnore
    private String password;

    @JsonView(SecurityViews.User.class)
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(SecurityViews.Admin.class)
    private Set<Role> roles;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonView(SecurityViews.User.class)
    private Set <Order> orders;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonView(SecurityViews.User.class)
    private Set<StashedProducts> cartProduct;

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
    public void setCartProducts(Set<StashedProducts> cartProducts) {
        if (this.cartProduct == null) {
            this.cartProduct = cartProducts;
        } else {
            this.cartProduct.retainAll(cartProducts);
            this.cartProduct.addAll(cartProducts);
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
