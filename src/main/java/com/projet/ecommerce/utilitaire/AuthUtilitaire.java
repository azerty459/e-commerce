package com.projet.ecommerce.utilitaire;

import com.projet.ecommerce.entrypoint.authentification.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class AuthUtilitaire {
    private static final String API_KEY = "kyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNTMwNjA0NjkxLCJzdWIiOiJhZG1pbiIsImlzcyI6ImEiLCJleHAiOjE1MzA2MDQ3OTF9.QzkzBu2kQG8bBpRejuLluPWNH-Yzm6Xe83zCTn-ioSM";

    /**
     * Permet de génerer un token de connection
     *
     * @param id        l'id a associer au token
     * @param issuer    le demandeur du token
     * @param subject   le sujet associer au token
     * @param ttlMillis le temps avant expiration du token
     * @return
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //La signature JWT utilisé dans l'algorithme pour enregistrer le token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // on utilise une clé api pour personnaliser l'encryptage
        // TODO sécuriser la clé api
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(API_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // On personalise le json
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // On rajoute une expiration si demandé
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // On serialize
        return builder.compact();
    }

    /**
     * Permet de verifier que le token existe
     *
     * @param jwt le string representant le token
     * @return le token si il existe
     */
    public static Token parseJWT(String jwt) {
        Token token = new Token();
        // Cette ligne throw un expection si le token n'existe pas
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(API_KEY))
                .parseClaimsJws(jwt).getBody();
        token.setId(claims.getId());
        token.setSubject(claims.getSubject());
        token.setToken(jwt);
        token.setExpiration(claims.getExpiration());
        token.setIssuer(claims.getIssuer());
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
        return token;
    }
}


