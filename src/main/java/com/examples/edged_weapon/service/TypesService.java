package com.examples.edged_weapon.service;

import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.Types;
import com.examples.edged_weapon.repo.TypesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class TypesService {
    private final TypesRepository typesRepository;

    public Types findById(Long id) {
        return typesRepository.findById(id).orElse(null);
    }

    public Types findByName(String name) {
        return typesRepository.findByName(name).orElse(null);
    }

    public Set<Types> findAll() {
        return new HashSet<>(typesRepository.findAll());
    }

    public Types create(Types type) {
        type.setCreated(new Date());
        type.setUpdated(new Date());
        return typesRepository.save(type);
    }

    public Types update(Types type) {
        Types targetProductType = typesRepository.findById(type.getId()).orElse(null);

        if (targetProductType == null) {
            throw new SpringEdgedWeaponException("type not found");
        }

        targetProductType.setName(type.getName());
        targetProductType.setUpdated(new Date());

        return typesRepository.save(type);
    }

    public void delete(Long id) {
        typesRepository.deleteById(id);
    }
}

