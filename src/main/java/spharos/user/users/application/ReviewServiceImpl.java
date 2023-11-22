package spharos.user.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.user.global.common.response.ResponseCode;
import spharos.user.global.exception.CustomException;
import spharos.user.users.domain.User;
import spharos.user.users.dto.ReviewWriterDto;
import spharos.user.users.infrastructure.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final UserRepository userRepository;
    @Override
    public List<ReviewWriterDto> retrieveReviewWriter(List<String> userEmailList) {

        List<ReviewWriterDto> reviewWriterDtoList = new ArrayList<>();

        for (String email: userEmailList) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_USER));

            ReviewWriterDto reviewWriterDto = ReviewWriterDto.builder()
                    .email(user.getEmail())
                    .nickName(user.getNickname())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();

            reviewWriterDtoList.add(reviewWriterDto);
        }
        return reviewWriterDtoList;
    }
}
