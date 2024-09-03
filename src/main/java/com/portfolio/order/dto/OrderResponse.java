package com.portfolio.order.dto;

import java.util.List;

import com.portfolio.order.dto.OrderDto.MyPageReviewListResult;
import com.portfolio.order.dto.OrderDto.OrderListResult;

import lombok.Getter;
import lombok.Setter;

public class OrderResponse {

    @Getter
    @Setter
	public static class OrderListResponse{
		List<OrderListResult> orderList;
	}
    @Getter
    @Setter
	public static class MyPageReviewListResponse{
    	List<MyPageReviewListResult> myPageReviewList;
	}
}
