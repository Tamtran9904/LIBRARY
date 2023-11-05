package sparkminds.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparkminds.library.dto.response.ErrorResponse;
import sparkminds.library.exception.DataInValidException;
import sparkminds.library.exception.VerificationLinkInValidException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = DataInValidException.class)
    public ErrorResponse expiredJwt(DataInValidException ex) {
        return ErrorResponse.builder()
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .errors(ex.getMessage())
                            .build();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ErrorResponse expiredJwt(MethodArgumentNotValidException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = VerificationLinkInValidException.class)
    public ErrorResponse expiredJwt(VerificationLinkInValidException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = MailException.class)
    public ErrorResponse expiredJwt(MailException ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .errors(ex.getMessage())
            .build();
    }
}
