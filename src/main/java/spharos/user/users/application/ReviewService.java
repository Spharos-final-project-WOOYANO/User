package spharos.user.users.application;

import spharos.user.users.dto.ReviewWriterDto;

import java.util.List;

public interface ReviewService {
    List<ReviewWriterDto> retrieveReviewWriter(List<String> userEmail);

}
