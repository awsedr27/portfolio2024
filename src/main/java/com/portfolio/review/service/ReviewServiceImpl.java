package com.portfolio.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.common.CommonEnum;
import com.portfolio.common.UserContext;
import com.portfolio.exception.CustomException;
import com.portfolio.order.dao.OrderDao;
import com.portfolio.order.dao.OrderItemsDao;
import com.portfolio.order.dto.OrderDto;
import com.portfolio.order.dto.OrderDto.OrderItemDto;
import com.portfolio.review.dao.ReviewDao;
import com.portfolio.review.dto.ReviewDto;
import com.portfolio.review.dto.ReviewDto.ReviewDeleteQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListQuery;
import com.portfolio.review.dto.ReviewDto.ReviewListResult;
import com.portfolio.review.dto.ReviewDto.ReviewListWithCount;
import com.portfolio.review.dto.ReviewDto.ReviewSaveQuery;
import com.portfolio.review.dto.ReviewDto.ReviewUpdateQuery;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewUpdateServiceDto;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	
	@Autowired
	OrderItemsDao orderItemsDao;
	
	@Autowired
	OrderDao orderDao;
	
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
		String userId=userContext.getUserInfo().getUserId();
		OrderItemDto orderItem=orderItemsDao.selectOrderItem(rq.getOrderItemId());
		if(orderItem==null) {
			throw new CustomException("잘못된 주문아이템번호입니다");
		}
		OrderDto order=orderDao.selectOrder(orderItem.getOrderId());
		if(order==null) {
			throw new CustomException("잘못된 주문아이템번호입니다!");
		}
		if(!order.getUserId().equals(userId)) {
			throw new CustomException("내 주문이 아닙니다");
		}
		if(!orderItem.getStatus().equals(CommonEnum.OrderItemStatus.COMPLETED.name())) {
			throw new CustomException("완료된 주문아이템이 아닙니다");
		}
		
		ReviewDto review=reviewDao.selectReviewByOrderItemId(rq.getOrderItemId());
		if(review!=null) {
			throw new CustomException("이미 리뷰를 작성한 주문아이템입니다");
		}
		
		ReviewSaveQuery reviewSaveQuery=new ReviewSaveQuery(rq);
		
		return reviewDao.insertReview(reviewSaveQuery);
	}

	@Override
	public int updateReview(ReviewUpdateServiceDto rq) {
		String userId=userContext.getUserInfo().getUserId();
		ReviewDto review=reviewDao.selectReview(rq.getReviewId());
		if(review==null) {
			throw new CustomException("잘못된 리뷰아이디입니다");
		}
		if("N".equals(review.getUseYn())) {
			throw new CustomException("삭제된 리뷰는 수정할 수 없습니다");
		}
		if (review.getReply() != null && !review.getReply().trim().isEmpty()) {
		    throw new CustomException("판매자 답글이 존재하면 리뷰를 수정할 수 없습니다");
		}
		OrderItemDto orderItem=orderItemsDao.selectOrderItem(review.getOrderItemId());
		if(orderItem==null) {
			throw new CustomException("잘못된 요청입니다");
		}
		OrderDto order=orderDao.selectOrder(orderItem.getOrderId());
		if(order==null) {
			throw new CustomException("잘못된 요청입니다!");
		}
		if(!order.getUserId().equals(userId)) {
			throw new CustomException("내 리뷰가 아닙니다");
		}
		ReviewUpdateQuery reviewUpdateQuery=new ReviewUpdateQuery(rq);
		
		return reviewDao.updateReview(reviewUpdateQuery);
	}

	@Override
	public int deleteReview(Integer reviewId) {
		String userId=userContext.getUserInfo().getUserId();
		ReviewDto review=reviewDao.selectReview(reviewId);
		if(review==null) {
			throw new CustomException("잘못된 리뷰아이디입니다");
		}
		if("N".equals(review.getUseYn())) {
			throw new CustomException("이미 삭제된 리뷰입니다");
		}
		OrderItemDto orderItem=orderItemsDao.selectOrderItem(review.getOrderItemId());
		if(orderItem==null) {
			throw new CustomException("잘못된 요청입니다");
		}
		OrderDto order=orderDao.selectOrder(orderItem.getOrderId());
		if(order==null) {
			throw new CustomException("잘못된 요청입니다!");
		}
		if(!order.getUserId().equals(userId)) {
			throw new CustomException("내 리뷰가 아닙니다");
		}
		ReviewDeleteQuery reviewDeleteQuery=new ReviewDeleteQuery();
		reviewDeleteQuery.setReviewId(reviewId);
		reviewDeleteQuery.setUseYn("N");
		return reviewDao.deleteReview(reviewDeleteQuery);
	}

}
