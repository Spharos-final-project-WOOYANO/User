package spharos.user.users.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.user.users.domain.User;
import spharos.user.users.dto.ReviewWriterDto;
import spharos.user.users.infrastructure.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final UserRepository userRepository;
    @Override
    public List<ReviewWriterDto> retrieveReviewWriter(List<String> userEmail) {

            List<User> users = userEmail.stream()
                .map(userRepository::findByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

            return users.stream()
                    .map(user -> ReviewWriterDto.builder()
                            .email(user.getEmail())
                            .userId(user.getId())
                            .nickname(user.getNickname())
                            .ImgUrl(user.getProfileImageUrl())
                            .build())
                    .toList();
    }
}
