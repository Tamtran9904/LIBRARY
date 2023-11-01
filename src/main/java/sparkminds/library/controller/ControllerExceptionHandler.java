package sparkminds.library.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sparkminds.library.dto.response.ErrorResponse;
import sparkminds.library.exception.InValidPasswordRegex;
import sparkminds.library.exception.OtpExpiredTime;
import sparkminds.library.exception.UnKnownOtp;
import sparkminds.library.exception.UnKnownVerificationToken;
import sparkminds.library.exception.UnknownPersonAccount;
import sparkminds.library.exception.VerifyLinkExpiredTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = InValidPasswordRegex.class)
    public ErrorResponse expiredJwt(InValidPasswordRegex ex) {
        return ErrorResponse.builder()
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .message("Need minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
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

    @ExceptionHandler(value = UnknownPersonAccount.class)
    public ErrorResponse expiredJwt(UnknownPersonAccount ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND)
            .message("Person Account doesn't exist in Database")
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = UnKnownVerificationToken.class)
    public ErrorResponse expiredJwt(UnKnownVerificationToken ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND)
            .message("Verification Token is invalid")
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = UnKnownOtp.class)
    public ErrorResponse expiredJwt(UnKnownOtp ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND)
            .message("Otp doesn't exist")
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = OtpExpiredTime.class)
    public ErrorResponse expiredJwt(OtpExpiredTime ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .message("Otp is expired time")
            .errors(ex.getMessage())
            .build();
    }

    @ExceptionHandler(value = VerifyLinkExpiredTime.class)
    public ErrorResponse expiredJwt(VerifyLinkExpiredTime ex) {
        return ErrorResponse.builder()
            .status(HttpStatus.NOT_ACCEPTABLE)
            .message("VerifyLink is expired time")
            .errors(ex.getMessage())
            .build();
    }
}