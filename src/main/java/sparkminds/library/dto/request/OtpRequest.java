package sparkminds.library.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import sparkminds.library.validation.annotation.OtpConstraint;
@Data
@RequiredArgsConstructor
public class OtpRequest {

    @OtpConstraint
    String code;

    String email;

}
