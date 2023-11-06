package sparkminds.library.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Person;
import sparkminds.library.entities.Session;
import sparkminds.library.exception.TokenException;
import sparkminds.library.repository.PersonRepository;
import sparkminds.library.repository.SessionRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${JWT_EXPIRED_ADDING}")
    private Long jwtExpiredAdding;

    @Value("${JWT_REFRESH_EXPIRED_ADDING}")
    private Long jwtRefreshExpiredAdding;

    @Value("${SECRET_KEY}")
    private String secretKey;
    
    private final PersonRepository personRepository;
    
    private final SessionRepository sessionRepository;

    public String generateToken(UserDetails userDetails, String refreshToken) {
        if (Boolean.FALSE.equals(isRefreshToken(refreshToken))) {
            throw new TokenException("It's not a refreshToken");
        }
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("isRefreshToken", false);
        String jti = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(refreshToken).getBody().getId();
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setId(jti)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiredAdding))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    @Transactional (rollbackOn = Exception.class)
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("isRefreshToken", true);
        String jti = generateJti(userDetails);
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setId(jti)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpiredAdding))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    @Transactional (rollbackOn = Exception.class)
    public String generateJti(UserDetails userDetails) {
        if (personRepository.findByEmail(userDetails.getUsername()).isEmpty()) {
            throw new UsernameNotFoundException(userDetails.getUsername());
        }
        Session session = new Session();
        Person person = personRepository.findByEmail(userDetails.getUsername()).get();
        session.setJti(UUID.randomUUID().toString());
        session.setPersonId(person);
        sessionRepository.save(session);
        return session.getJti().toString();
    }

    public Claims parseJwt(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {1}", e);
            throw new JwtException("Expired JWT token");
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {1}", e);
            throw new JwtException("Invalid JWT signature");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {1}", e);
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {1}", e);
            throw new JwtException("JWT token compact of handler are invalid");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {1}", e);
            throw new JwtException("Invalid JWT token");
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return parseJwt(token).getSubject();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        if (userName.equals(userDetails.getUsername())) {
            return Boolean.FALSE.equals(isRefreshToken(token));
        }
        return false;
    }

    public Boolean isRefreshToken(String refreshToken) {
       return (Boolean) parseJwt(refreshToken).get("isRefreshToken");
    }
}
