package com.portfolio.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.exception.CustomException;
import com.portfolio.review.dto.ReviewDto.ReviewListWithCount;
import com.portfolio.review.dto.ReviewRequest.ReviewDeleteRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewListRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewSaveRequest;
import com.portfolio.review.dto.ReviewRequest.ReviewUpdateRequest;
import com.portfolio.review.dto.ReviewResponse.ReviewListResponse;
import com.portfolio.review.dto.ReviewServiceDto.ReviewListServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewSaveServiceDto;
import com.portfolio.review.dto.ReviewServiceDto.ReviewUpdateServiceDto;
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
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 작성을 실패했습니다");
    	}catch (Exception e) {
    		log.error("리뷰 작성 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 작성을 실패했습니다");
		}
    
    }
    @PostMapping("/update")
    public ResponseEntity<?> reviewUpdate(@Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		ReviewUpdateServiceDto rq =new ReviewUpdateServiceDto(reviewUpdateRequest);
    		int result=reviewService.updateReview(rq);
    	    return ResponseEntity.status(HttpStatus.OK).body("리뷰수정을 완료했습니다");
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 수정을 실패했습니다");
    	}catch (Exception e) {
    		log.error("리뷰 수정 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 수정을 실패했습니다");
		}
    }
    @PostMapping("/delete")
    public ResponseEntity<?> reviewDelete(@Valid @RequestBody ReviewDeleteRequest reviewDeleteRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		int result=reviewService.deleteReview(reviewDeleteRequest.getReviewId());
    	    return ResponseEntity.status(HttpStatus.OK).body("리뷰삭제를 완료했습니다");
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 삭제 실패했습니다");
    	}catch (Exception e) {
    		log.error("리뷰 삭제 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제 실패했습니다");
		}
    }
}
