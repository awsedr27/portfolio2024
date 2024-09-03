package com.portfolio.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.common.UserContext;
import com.portfolio.review.dao.ReviewDao;
import com.portfolio.review.dto.ReviewDto.ReviewListQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListResult;
import com.portfolio.review.dto.ReviewDto.ReviewListWithCount;
import com.portfolio.review.dto.ReviewDto.ReviewSaveQuery;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	
    @Value("${review.paging.size}")
    private int pagingSize;
    
	@Autowired
	UserContext userContext;

	@Override
	public ReviewListWithCount getReviewListWithCount(ReviewListServiceDto rq) {
		ReviewListQuery reviewListQuery=new ReviewListQuery(rq);
		int reviewCnt=reviewDao.selectReviewCnt(rq.getProductId());
		reviewListQuery.setLimit(pagingSize);
		List<ReviewListResult> reivewList=reviewDao.selectReviewList(reviewListQuery);
		ReviewListWithCount rs=new ReviewListWithCount(reivewList,reviewCnt);
		return rs;
	}

	@Override
	public int saveReview(ReviewSaveServiceDto rq) {
		ReviewSaveQuery reviewSaveQuery=new ReviewSaveQuery(rq);
		reviewSaveQuery.setUserId(userContext.getUserInfo().getUserId());
		
		return reviewDao.insertReview(reviewSaveQuery);
	}

}
