package sparkminds.library.controller;

import io.jsonwebtoken.JwtException;
import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparkminds.library.dto.response.ErrorResponse;
import sparkminds.library.exception.DataInValidException;
import sparkminds.library.exception.VerificationLinkInValidException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = DataInValidException.class)
    public ErrorResponse dataInValidException(DataInValidException ex) {
        return ErrorResponse.builder()
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .errors(ex.getMessage())
                            .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = VerificationLinkInValidException.class)
    public ErrorResponse verificationLinkInValidException(VerificationLinkInValidException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = MailException.class)
    public ErrorResponse mailException(MailException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ErrorResponse authenticationException(BadCredentialsException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = LockedException.class)
    public ErrorResponse authenticationException(LockedException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ErrorResponse usernameNotFoundException(UsernameNotFoundException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.FORBIDDEN)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = JwtException.class)
    public ErrorResponse jwtException(JwtException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.UNAUTHORIZED)
            .errors(ex.getMessage())
            .build();
    }
}
