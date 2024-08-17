package com.portfolio.review.service;

import java.util.List;

import com.portfolio.review.dto.ReviewDto.ReviewListResult;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;

public interface ReviewService {

	List<ReviewListResult> getReviewList(ReviewListServiceDto rq);

	int saveReview(ReviewSaveServiceDto rq);

}
