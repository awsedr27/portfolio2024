package com.portfolio.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.review.dto.ReviewDto.ReviewListQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListResult;
import com.portfolio.review.dto.ReviewDto.ReviewSaveQuery;

@Mapper
public interface ReviewDao {

	List<ReviewListResult> selectReviewList(ReviewListQuery reviewListQuery);

	int insertReview(ReviewSaveQuery reviewSaveQuery);

}
