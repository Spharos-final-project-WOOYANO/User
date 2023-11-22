package spharos.user.users.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewWriterResponse {

    private String reviewWriterEmail;
    private String reviewWriterNickName;
    private String reviewWriterProfileImageUrl;

}
