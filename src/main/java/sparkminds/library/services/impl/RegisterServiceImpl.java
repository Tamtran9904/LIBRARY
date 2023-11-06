package sparkminds.library.services.impl;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparkminds.library.dto.request.RegisterRequest;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.Customer;
import sparkminds.library.enums.AccountStatus;
import sparkminds.library.enums.Role;
import sparkminds.library.exception.DataInValidException;
import sparkminds.library.mapper.MapperServiceImpl;
import sparkminds.library.repository.CustomerRepository;
import sparkminds.library.repository.RoleRepository;
import sparkminds.library.services.RegisterService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {
    
    private final CustomerRepository customerRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapperServiceImpl mapperService;

    private final SendingEmailServiceImpl verifyEmailService;

    @Transactional (rollbackOn = Exception.class)
    public RegisterResponse register(RegisterRequest registerRequest, HttpServletRequest httpServletRequest)
        throws MessagingException, UnsupportedEncodingException {
        if (customerRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            log.error("User have been register");
            throw new DataInValidException("User have been register");
        }
        Customer customer = mapperService.convertToEntity(registerRequest, Customer.class);
        customer.setRoleUserId(roleRepository.findByRole(Role.CUSTOMER));
        customer.setAccountStatus(AccountStatus.PENDING);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);

        verifyEmailService.sendVerificationEmail(customer, httpServletRequest.getRequestURL().toString());

        return RegisterResponse.builder().status(HttpStatus.OK).message("Success Sign Up").build();
    }


}
