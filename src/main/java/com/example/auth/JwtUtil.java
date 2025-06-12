package com.example.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Date;

public class JwtUtil {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String JWT_SECRET_KEY = dotenv.get("JWT_SECRET_KEY");
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 12; // expiration time for token!

    private static final Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);

    public static String generateToken(int userId, String email) {
        return JWT.create()
                .withIssuer("CraftoriaS")
                .withClaim("email", email)
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken (String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("CraftoriaS")
                .build();
        return verifier.verify(token);
    }
}
