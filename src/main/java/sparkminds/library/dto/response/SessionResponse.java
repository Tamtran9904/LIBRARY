package sparkminds.library.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponse {

    private String accessToken;

    private String refreshToken;

}
