package sparkminds.library.controller;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparkminds.library.dto.request.OtpRequest;
import sparkminds.library.dto.request.RegisterRequest;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.services.impl.RegisterAdminServiceImpl;
import sparkminds.library.services.impl.SendingEmailServiceImpl;
import sparkminds.library.validation.annotation.OtpConstraint;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterAdminServiceImpl registerService;

    private final SendingEmailServiceImpl verifyEmailService;

    @PostMapping("/admin")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException {
       return ResponseEntity.ok(registerService.register(registerRequest, request));
    }

    @GetMapping("/admin/verify")
    public ResponseEntity<RegisterResponse> afterVerify(@RequestParam String code, @RequestParam Long id) {
       return ResponseEntity.ok(verifyEmailService.isVerificationToken(code, id));
    }

    @PostMapping("enterOtp")
    public ResponseEntity<RegisterResponse> enterOtp (@Valid @RequestBody OtpRequest otpRequest) {
        return ResponseEntity.ok(verifyEmailService.enterOtp(otpRequest.getCode(), otpRequest.getEmail()));
    }

    @GetMapping("/admin/reSend")
    public void reSendOtp(@RequestParam Long id, HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException {
        verifyEmailService.reSendOtp(id, request);
    }

}
