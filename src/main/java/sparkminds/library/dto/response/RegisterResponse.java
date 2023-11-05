package sparkminds.library.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class RegisterResponse {

    private HttpStatus status;

    private String message;

}
