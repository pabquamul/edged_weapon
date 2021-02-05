package com.examples.edged_weapon.repo;

import com.examples.edged_weapon.models.Types;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypesRepository extends JpaRepository<Types, Long> {
    Optional<Types> findByName(String name);
}
