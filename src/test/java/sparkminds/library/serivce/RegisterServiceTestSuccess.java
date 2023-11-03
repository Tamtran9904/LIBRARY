package sparkminds.library.serivce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import sparkminds.library.dto.request.RegisterRequest;
import sparkminds.library.dto.response.RegisterResponse;
import sparkminds.library.entities.Admin;
import sparkminds.library.entities.RoleUser;
import sparkminds.library.enums.Gender;
import sparkminds.library.mapper.MapperServiceImpl;
import sparkminds.library.repository.AdminRepository;
import sparkminds.library.repository.RoleRepository;
import sparkminds.library.services.impl.RegisterAdminServiceImpl;
import sparkminds.library.services.impl.SendingEmailServiceImpl;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTestSuccess {

    @Mock
    AdminRepository adminRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SendingEmailServiceImpl verifyEmailService;

    @Mock
    MapperServiceImpl mapperService;

    RegisterAdminServiceImpl registerAdminService;

    @BeforeEach
    void setUp() {
        mapperService = new MapperServiceImpl(modelMapper);
        registerAdminService = new RegisterAdminServiceImpl(adminRepository, roleRepository, passwordEncoder, mapperService, verifyEmailService);
    }

    @Test
    void whenGetTheOneHaveBeenRegister() throws MessagingException, UnsupportedEncodingException {
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        RegisterRequest registerRequest = new RegisterRequest();

        RoleUser roleUser = new RoleUser();
        registerRequest.setName("abc");
        registerRequest.setEmail("a@gmail.com");
        registerRequest.setAddress("154");
        registerRequest.setPassword("aaa");
        registerRequest.setPhone("090");
        registerRequest.setGender(Gender.MALE);

        RegisterResponse response = RegisterResponse.builder().status(HttpStatus.OK).message("Success Sign Up").build();

        Admin adminEntity = new Admin();
        adminEntity.setName("abc");
        adminEntity.setEmail("a@gmail.com");
        adminEntity.setAddress("154");
        adminEntity.setPassword("aaa");
        adminEntity.setPhone("090");
        adminEntity.setGender(Gender.MALE);

        when(adminRepository.findByEmail(registerRequest.getEmail())).thenReturn(null);
        when(mapperService.convertToEntity(registerRequest, Admin.class)).thenReturn(adminEntity);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("Sign URL"));
        assertThat(registerAdminService.register(registerRequest, httpServletRequest)).isEqualTo(response);
        verify(adminRepository, times(1)).findByEmail(registerRequest.getEmail());
    }

}
