package com.portfolio.review.dto;

import java.sql.Timestamp;

import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;

import lombok.Getter;
import lombok.Setter;

public class ReviewDto {

    @Getter
    @Setter
	public static class ReviewListQuery {
    	public ReviewListQuery(ReviewListServiceDto rq) {
			this.productId=rq.getProductId();
			this.sortBy=rq.getSortBy();
			this.reviewId=rq.getReviewId();
		}
		private Integer productId;
        private String sortBy;
        private Integer reviewId;
	    private int limit;
	}
    @Getter
    @Setter
	public static class ReviewListResult {
    	private Integer ReviewId;
    	private Integer productId;
        private Integer rating;
        private String comment;
        private String reply;
        private Timestamp createDate;
        private Timestamp modifyDate;
	}
    @Getter
    @Setter
	public static class ReviewSaveQuery {
    	public ReviewSaveQuery(ReviewSaveServiceDto rq) {
    		this.productId=rq.getProductId();
    		this.rating=rq.getRating();
    		this.comment=rq.getComment();
		}
    	private Integer productId;
    	private Integer rating;
    	private String comment;
    	private String userId;
	}
}
