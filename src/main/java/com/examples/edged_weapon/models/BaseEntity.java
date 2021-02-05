package com.examples.edged_weapon.models;

import com.examples.edged_weapon.views.SecurityViews;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity {

    @Id
    @JsonView(SecurityViews.Anonymous.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonView(SecurityViews.Admin.class)
    @CreatedDate
    Date created;

    @JsonView(SecurityViews.Admin.class)
    @LastModifiedDate
    Date updated;

}
