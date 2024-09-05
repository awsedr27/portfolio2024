package com.portfolio.review.dto;

import com.portfolio.review.dto.ReviewRequest.ReviewListRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewSaveRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewUpdateRequest;

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
			this.orderItemId=reviewSaveRequest.getOrderItemId();
			this.rating=reviewSaveRequest.getRating();
			this.comment=reviewSaveRequest.getComment();
			
		}
    	private Integer orderItemId;
    	private Integer rating;
    	private String comment;
	}
    @Getter
    @Setter
	public static class ReviewUpdateServiceDto {
    	public ReviewUpdateServiceDto(ReviewUpdateRequest rq) {
			this.reviewId=rq.getReviewId();
			this.rating=rq.getRating();
			this.comment=rq.getComment();
			
		}
    	private Integer reviewId;
    	private Integer rating;
    	private String comment;
	}
}
