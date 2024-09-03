package com.portfolio.review.dto;

import java.sql.Timestamp;
import java.util.List;

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
			this.rating=rq.getRating();
		}
		private Integer productId;
        private String sortBy;
        private Integer reviewId;
        private Integer rating;
	    private int limit;
	}
    @Getter
    @Setter
	public static class ReviewListResult {
    	private Integer reviewId;
    	private String userName;
        private Integer rating;
        private String comment;
        private String reply;
        private Timestamp createDate;
	}
    @Getter
    @Setter
	public static class ReviewListWithCount{
    	List<ReviewListResult> reviewList;
    	int reviewCnt;
    	public ReviewListWithCount() {
    		
    	}
    	public ReviewListWithCount(List<ReviewListResult> reviewList,int reviewCnt) {
    		this.reviewList=reviewList;
    		this.reviewCnt=reviewCnt;
    	}
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
