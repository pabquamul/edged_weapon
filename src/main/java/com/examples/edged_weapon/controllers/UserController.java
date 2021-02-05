package com.examples.edged_weapon.controllers;

import com.examples.edged_weapon.dto.*;
import com.examples.edged_weapon.exceptions.ConflictException;
import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.User;
import com.examples.edged_weapon.repo.UserRepository;
import com.examples.edged_weapon.service.AuthService;
import com.examples.edged_weapon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/view/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

//    @GetMapping("/users")
//    public List<User> getUsers(){
//        return userRepository.findAll();
//    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers (@PageableDefault(size = 20, sort = {"created"}, direction = Sort.Direction.DESC)Pageable pageable){
        JsonPage<User> users = new JsonPage<>(userService.findAll(pageable),pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
        public ResponseEntity getUserById(@PathVariable(name = "id", required = true) Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    @PatchMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto){
       try{
           User updatedUser = userService.update(updateUserRequestDto, id);
           return ResponseEntity.ok(updatedUser);
       }catch (ConflictException e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getBody());
       }
    }



    @PostMapping("/users")
    void addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
