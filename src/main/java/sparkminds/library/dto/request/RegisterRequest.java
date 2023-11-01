package sparkminds.library.dto.request;

import javax.validation.constraints.Email;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sparkminds.library.enums.Gender;
import sparkminds.library.validation.annotation.PasswordConstraint;

@Data
@RequiredArgsConstructor
public class RegisterRequest {

    @PasswordConstraint
    private String password;

    private String name;

    private Gender gender;

    private String phone;

    @Email (message = "Email is invalid")
    private String email;

    private String address;
}
