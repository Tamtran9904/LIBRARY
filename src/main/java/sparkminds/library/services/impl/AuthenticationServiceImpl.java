package sparkminds.library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.request.LoginRequest;
import sparkminds.library.dto.request.RegisterRequest;
import sparkminds.library.entities.Admin;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.Role;
import sparkminds.library.repository.AdminRepository;
import sparkminds.library.repository.RoleRepository;
import sparkminds.library.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final AdminRepository adminRepository;

    public String authenticate(LoginRequest request)
        throws UsernameNotFoundException, Exception {
        String jwt = null;
        try {
            if (authenticate(request.getEmail(), request.getPassword())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
                jwt = jwtService.generateToken(userDetails);
                return jwt;
            } else {
                String msg = "Incorrect username or password, please login again!";
                return msg;
            }
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isActived(String userName, String password) {
        Admin admin = adminRepository.findByEmail(userName);
        return true;
    }

    private boolean authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            return true;
        } catch (DisabledException e) {
            e.printStackTrace();
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return false;
    }


}
