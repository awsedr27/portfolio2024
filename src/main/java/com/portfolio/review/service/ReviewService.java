package com.portfolio.review.service;

import com.portfolio.review.dto.ReviewDto.ReviewListWithCount;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewUpdateServiceDto;

public interface ReviewService {

	ReviewListWithCount getReviewListWithCount(ReviewListServiceDto rq);

	int saveReview(ReviewSaveServiceDto rq);

	int updateReview(ReviewUpdateServiceDto rq);

	int deleteReview(Integer reviewId);

}
