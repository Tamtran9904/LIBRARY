package sparkminds.library.services.impl;

import java.time.Instant;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.request.LoginRequest;
import sparkminds.library.dto.response.SessionResponse;
import sparkminds.library.entities.Person;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.exception.TokenException;
import sparkminds.library.repository.PersonRepository;
import sparkminds.library.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final PersonRepository personRepository;

    @Value("${TIME_BLOCKING}")
    private Integer timeBlock;

    public SessionResponse login(LoginRequest request) {
        try {
            checkBlock(request);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            reNewBlockNumber(request);
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            String accessToken = jwtService.generateToken(userDetails, refreshToken);
            return SessionResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        } catch (DisabledException e) {
            log.error("the account is disabled");
            throw new DisabledException("the account is disabled");
        } catch (BadCredentialsException e) {
            log.error("the password are invalid");
            handleLoginFail(request);
            throw new BadCredentialsException("the password are invalid");
        } catch (LockedException e) {
            log.error("the account is locked");
            throw new LockedException("the account is Blocked for 30 mins");
        }
    }

    public SessionResponse getNewToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization").substring(7);
        if(Boolean.FALSE.equals(jwtService.isRefreshToken(refreshToken))) {
            throw new TokenException("It's not a refreshToken");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(refreshToken));
        String accessToken = jwtService.generateToken(userDetails, refreshToken);
        return SessionResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public void handleLoginFail(LoginRequest request) {
        Optional<Person> optional = personRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("UserName not found");
        }
        Person person = optional.get();
        person.setNumberLoginFail(person.getNumberLoginFail() + 1);
        if (person.getNumberLoginFail() >= 3) {
            person.setAccountStatus(AccountStatus.BLOCKED);
            person.setTimeBlock(Instant.now().plusSeconds(timeBlock));
        }
        personRepository.save(person);
    }

    public void checkBlock(LoginRequest request) {
        Optional<Person> optional = personRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("UserName not found");
        }
        Person person = optional.get();
        if (person.getAccountStatus() == AccountStatus.BLOCKED) {
            if (person.getTimeBlock().compareTo(Instant.now()) < 0) {
                person.setAccountStatus(AccountStatus.ACTIVE);
                personRepository.save(person);
            }
        }
    }

    public void reNewBlockNumber(LoginRequest request) {
        Optional<Person> optional = personRepository.findByEmail(request.getEmail());
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("UserName not found");
        }
        Person person = optional.get();
        person.setNumberLoginFail(0);
        personRepository.save(person);
    }
}
