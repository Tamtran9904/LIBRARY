package sparkminds.library.security;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Customer;
import sparkminds.library.models.CustomUserDetails;
import sparkminds.library.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        Customer customer = customerOptional.get();
        List<GrantedAuthority> roleNames = List
            .of(new SimpleGrantedAuthority(customer.getRoleUserId().getRole().name()));
        return new CustomUserDetails(customer.getEmail(), customer.getPassword(), roleNames, customer.getAccountStatus());
    }
}
