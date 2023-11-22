package spharos.user.users.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spharos.user.users.application.ReviewService;
import spharos.user.users.dto.ReviewWriterDto;
import spharos.user.users.vo.response.ReviewWriterResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성자 정보 조회",
            description = "review-bookmark에서 해당 업체에 리뷰를 작성한 유저들의 email을 리스트에 담아 요청",
            tags = { "Retrieve Review Writer User" })
    @PostMapping("/review/writer")
    public List<ReviewWriterResponse> getReviewWriter(@RequestBody List<String> emailList) {

        List<ReviewWriterDto> reviewWriterDtoList = reviewService.retrieveReviewWriter(emailList);

        List<ReviewWriterResponse> reviewWriterResponseList = new ArrayList<>();

        for (ReviewWriterDto reviewWriterDto:reviewWriterDtoList) {

            ReviewWriterResponse reviewWriterResponse = ReviewWriterResponse.builder()
                    .reviewWriterEmail(reviewWriterDto.getEmail())
                    .reviewWriterNickName(reviewWriterDto.getNickName())
                    .reviewWriterProfileImageUrl(reviewWriterDto.getProfileImageUrl())
                    .build();

            reviewWriterResponseList.add(reviewWriterResponse);
        }
        return reviewWriterResponseList;
    }
}
