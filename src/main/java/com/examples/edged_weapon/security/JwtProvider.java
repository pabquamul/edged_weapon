/*
package com.examples.edged_weapon.security;

import com.examples.edged_weapon.exceptions.SpringEdgedWeaponException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("src/main/resources/springweapon.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringEdgedWeaponException("Ошибка при загрузке хранилища ключей");
        }
    }
//resourceAsStream
//FileOutputStream fos = new FileOutputStream("src/main/resources/springweapon.jks");
//            keyStore.store(fos,"secret".toCharArray());
//            fos.close();

    public String generateToken(Authentication authentication){
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try{
            return (PrivateKey) keyStore.getKey("springweapon", "secret".toCharArray());
        }catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e ){
            throw new SpringEdgedWeaponException("Ошибка при получении ключа");
        }
    }


}
*/