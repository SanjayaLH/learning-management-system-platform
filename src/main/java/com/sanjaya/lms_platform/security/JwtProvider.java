package com.sanjaya.lms_platform.security;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtProvider {

    private final String secret = "your-super-secret-key-at-least-32-chars-long!!";
    private final Key key = new HmacKey(secret.getBytes());

    public String generateToken(String email, String role) throws Exception {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(email);
        claims.setClaim("role", role);
        claims.setExpirationTimeMinutesInTheFuture(60);
        claims.setIssuedAtToNow();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(key);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        return jws.getCompactSerialization();
    }
}