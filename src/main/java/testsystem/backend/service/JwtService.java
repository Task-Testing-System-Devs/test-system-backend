package testsystem.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class provides methods to create, parse and validate JSON Web Tokens (JWT).
 */
@Component
public class JwtService {

    // The secret used to sign the JWTs. This must be kept secret.
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token JWT token from which to extract the username.
     * @return Username extracted from the JWT token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token JWT token from which to extract the expiration date.
     * @return Expiration date extracted from the JWT token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a claim from the given JWT token.
     *
     * @param token         JWT token from which to extract the claim.
     * @param claimsResolver A function that takes a Claims object and returns the desired claim.
     * @param <T>           The type of the claim.
     * @return The desired claim extracted from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token JWT token from which to extract all claims.
     * @return Claims object containing all claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token JWT token to check.
     * @return True if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the given JWT token.
     *
     * @param token       JWT token to validate.
     * @param userDetails UserDetails object corresponding to the user to whom the token was issued.
     * @return True if the token is valid for the given user, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (
                username.equals(userDetails.getUsername())
                        &&
                !isTokenExpired(token)
        );
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param userName Username for whom to generate the token.
     * @return Generated JWT token.
     */
    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    /**
     * Creates a new JWT token with the provided claims and username.
     *
     @param claims Claims to be included in the token.
     @param userName Username to be included in the token subject.
     @return JWT token as a string.
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 2 weeks.
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Retrieves the signing key used for generating JWT tokens.
     *
     @return Signing key as a Key object.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
