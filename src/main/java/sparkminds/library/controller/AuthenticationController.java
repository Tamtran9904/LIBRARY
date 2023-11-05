package sparkminds.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparkminds.library.dto.request.LoginRequest;
import sparkminds.library.services.impl.AuthenticationServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authenticate;

    @PostMapping("/authenticate")
    public String  authenticate(@RequestBody LoginRequest request) throws Exception{
        return authenticate.authenticate(request);
    }
}
