package com.examples.edged_weapon.service;

import com.examples.edged_weapon.dto.AuthenticationResponse;
import com.examples.edged_weapon.dto.LoginRequest;
import com.examples.edged_weapon.dto.RegisterRequest;
import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import com.examples.edged_weapon.models.NotificationEmail;
import com.examples.edged_weapon.models.Role;
import com.examples.edged_weapon.models.User;
import com.examples.edged_weapon.models.VerificationToken;
import com.examples.edged_weapon.repo.UserRepository;
import com.examples.edged_weapon.repo.VerificationTokenRepository;
//import com.examples.edged_weapon.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor

public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
 //   private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest)  {
        User user = new User();

//        Role roleUser = roleRepo.findByName("ROLE_USER").orElse(null);
//        if (roleUser==null){
//            throw new ServiceUnavailableException();
//        }
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(roleUser);

        user.setUsername(registerRequest.getUsername());
        user.setSurname(registerRequest.getSurname());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setActive(false);
//        user.setRoles(userRoles);
        user.setRoles(Collections.singleton(Role.USER));


        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Пожалуйста, подтвердите свою почту", user.getEmail(),
                "Спасибо за регистрацию, перейдите по ссылке ниже для подтверждения аккаунта: " +
                        "http://localhost:8080/api/auth/accountVerification/" + token ));

    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;

    }

    public void verifyAccount(String token) {
       Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
       verificationToken.orElseThrow(() -> new SpringEdgedWeaponException("не верный токен"));
       fetchUserAndActive(verificationToken.get());
    }

    @Transactional
    void fetchUserAndActive(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringEdgedWeaponException("нет пользователя с именем " + username));
        user.setActive(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
       // String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(loginRequest.getUsername());
    }
}
