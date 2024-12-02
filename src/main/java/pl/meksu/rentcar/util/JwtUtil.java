package pl.meksu.rentcar.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "my_secret_key_567_212";
    private Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public String generateToken(String username, String userId, String userType) {
        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withClaim("userType", userType)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            return jwt.getSubject().equals(username) && jwt.getExpiresAt().after(new Date());
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
