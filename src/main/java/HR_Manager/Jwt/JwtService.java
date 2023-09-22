package HR_Manager.Jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    //I think this one is better to use as compared to jwt helper of drugesh
    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    //ye key ham khud b dal skte hen ya phir encryption key geenerator se leke daal skte hen , minimum for jwt is 256bit

    //=getUsernameFromToken of durgesh
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //=getClaimFromToken of durgesh
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //same
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //=doGenerateToken of durgesh
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // thats  5hrs in total
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //=validateToken of durgesh
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }    // hmen check krna hia k jo user hmare paas hia usi ka username token me hai ya nai


    //same of drugesh
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    //==get Expiration date from token of udrgesh
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //=getAllClaims... of durgesh
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
