package com.portfolio.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.review.dto.ReviewDto;
import com.portfolio.review.dto.ReviewDto.ReviewDeleteQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListResult;
import com.portfolio.review.dto.ReviewDto.ReviewSaveQuery;
import com.portfolio.review.dto.ReviewDto.ReviewUpdateQuery;

@Mapper
public interface ReviewDao {

	List<ReviewListResult> selectReviewList(ReviewListQuery reviewListQuery);

	int insertReview(ReviewSaveQuery reviewSaveQuery);

	int selectReviewCnt(Integer productId);

	ReviewDto selectReviewByOrderItemId(Integer orderItemId);
	
	ReviewDto selectReview(Integer reviewId);

	int updateReview(ReviewUpdateQuery reviewUpdateQuery);

	int deleteReview(ReviewDeleteQuery reviewDeleteQuery);

}
