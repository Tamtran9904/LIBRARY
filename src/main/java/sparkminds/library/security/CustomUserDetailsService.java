package sparkminds.library.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sparkminds.library.models.CustomUserDetails;
import sparkminds.library.entities.Admin;
import sparkminds.library.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin;
        try {
            admin = adminRepository.findByEmail(email);
        } catch (UsernameNotFoundException exception) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> roleNames = List
            .of(new SimpleGrantedAuthority(admin.getRoleUserId().getRole().name()));
        return new CustomUserDetails(admin.getEmail(), admin.getPassword(), roleNames);
    }
}
