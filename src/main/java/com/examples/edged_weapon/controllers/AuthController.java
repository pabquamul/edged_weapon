package com.examples.edged_weapon.controllers;

import com.examples.edged_weapon.dto.AuthenticationResponse;
import com.examples.edged_weapon.dto.LoginRequest;
import com.examples.edged_weapon.dto.RegisterRequest;
import com.examples.edged_weapon.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signup")
    public String reg(){

        return "registration";
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup (@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("registration completed successfully!", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("account verified successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);

    }

}
