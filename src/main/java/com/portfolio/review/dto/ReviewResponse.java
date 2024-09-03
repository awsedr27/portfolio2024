package com.portfolio.review.dto;

import java.util.List;

import com.portfolio.review.dto.ReviewDto.ReviewListResult;

import lombok.Getter;
import lombok.Setter;

public class ReviewResponse {
    @Getter
    @Setter
	public static class ReviewListResponse{
		List<ReviewListResult> reviewList;
		int reviewCnt;
	}
}
