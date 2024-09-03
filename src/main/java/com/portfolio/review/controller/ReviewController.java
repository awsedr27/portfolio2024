package com.portfolio.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.review.dto.ReviewDto.ReviewListWithCount;
import com.portfolio.review.dto.ReviewRequest.ReviewListRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewSaveRequest;
import com.portfolio.review.dto.ReviewResponse.ReviewListResponse;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;
import com.portfolio.review.service.ReviewService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/review")
@Slf4j
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;

    @PostMapping("/list")
    public ResponseEntity<?> reivewList(@Valid @RequestBody ReviewListRequest reviewListRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		ReviewListServiceDto rq =new ReviewListServiceDto(reviewListRequest);
    		ReviewListWithCount result=reviewService.getReviewListWithCount(rq);
    		ReviewListResponse rs=new ReviewListResponse();
    		rs.setReviewCnt(result.getReviewCnt());
    		rs.setReviewList(result.getReviewList());
    	    return ResponseEntity.status(HttpStatus.OK).body(rs);
    	}catch (Exception e) {
    		log.error("리뷰 목록 불러오기 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 목록 불러오기 실패했습니다");
		}
    }
    @PostMapping("/save")
    public ResponseEntity<?> reviewSave(@Valid @RequestBody ReviewSaveRequest reviewSaveRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		ReviewSaveServiceDto rq =new ReviewSaveServiceDto(reviewSaveRequest);
    		int result=reviewService.saveReview(rq);
    	    return ResponseEntity.status(HttpStatus.OK).body("리뷰작성을 완료했습니다");
    	}catch (Exception e) {
    		log.error("리뷰 작성 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 작성을 실패했습니다");
		}
    
    }
}
