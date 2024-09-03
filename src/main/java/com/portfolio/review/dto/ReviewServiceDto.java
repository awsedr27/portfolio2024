package com.portfolio.review.dto;

import com.portfolio.review.dto.ReviewRequest.ReviewListRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ReviewServiceDto {
	
    @Getter
    @Setter
	public static class ReviewListServiceDto {
    	public ReviewListServiceDto(ReviewListRequest reviewListRequest) {
			this.productId=reviewListRequest.getProductId();
			this.sortBy=reviewListRequest.getSortBy();
			this.reviewId=reviewListRequest.getReviewId();
			this.rating=reviewListRequest.getRating();
		}
		private Integer productId;
        private String sortBy;
        private Integer reviewId;
        private Integer rating;
	}
    @Getter
    @Setter
	public static class ReviewSaveServiceDto {
    	public ReviewSaveServiceDto(ReviewSaveRequest reviewSaveRequest) {
			this.productId=reviewSaveRequest.getProductId();
			this.rating=reviewSaveRequest.getRating();
			this.comment=reviewSaveRequest.getComment();
			
		}
    	private Integer productId;
    	private Integer rating;
    	private String comment;
	}
}
