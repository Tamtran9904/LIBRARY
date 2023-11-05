package sparkminds.library.serivce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparkminds.library.dto.request.RegisterRequest;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.Admin;
import sparkminds.library.exception.UserHaveBeenRegister;
import sparkminds.library.repository.AdminRepository;
import sparkminds.library.services.impl.RegisterAdminServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTestThrowException {

    @InjectMocks
    RegisterAdminServiceImpl registerAdminService;

    @Mock
    AdminRepository adminRepository;

    @Test
    void whenGetTheOneHaveBeenRegister() throws MessagingException, UnsupportedEncodingException {
        HttpServletRequest httpServletRequest = null;
        UserHaveBeenRegister exception = new UserHaveBeenRegister("Your email have been register before");
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("abc");
        registerRequest.setEmail("a@gmail.com");
        registerRequest.setAddress("154");
        registerRequest.setPassword("aaa");

        RegisterResponse response = RegisterResponse.builder().build();

        Admin admin = new Admin();

        when(adminRepository.findByEmail(registerRequest.getEmail())).thenReturn(admin);

        assertThatThrownBy(() -> registerAdminService.register(registerRequest, httpServletRequest))
            .isInstanceOf(UserHaveBeenRegister.class);

        verify(adminRepository, times(1)).findByEmail(registerRequest.getEmail());
    }

}
