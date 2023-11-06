package sparkminds.library.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparkminds.library.dto.request.LoginRequest;
import sparkminds.library.dto.response.SessionResponse;
import sparkminds.library.services.impl.LoginServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    @PostMapping("/login")
    public ResponseEntity<SessionResponse> login(@RequestBody LoginRequest request) throws Exception{
        return ResponseEntity.ok(loginService.login(request));
    }

    @GetMapping("/get-new-token")
    public ResponseEntity<SessionResponse> getNewToken(HttpServletRequest request) {
        return ResponseEntity.ok(loginService.getNewToken(request));
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
