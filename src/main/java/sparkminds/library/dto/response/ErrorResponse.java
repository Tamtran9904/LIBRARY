package sparkminds.library.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {

    private HttpStatus status;

    private String message;

    private String errors;
}
