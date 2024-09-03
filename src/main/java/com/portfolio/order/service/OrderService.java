package com.portfolio.order.service;

import java.util.List;

import com.portfolio.order.dto.OrderDto.MyPageReviewListResult;
import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.MyPageReviewListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderSaveServiceDto;

public interface OrderService {

	List<OrderListResult> getOrderList(OrderListServiceDto orderListServiceDto);

	int cancelOrder(OrderCancelServiceDto orderCancelServiceDto);

	int cancelOrderItem(OrderItemCancelServiceDto orderItemCancelServiceDto);

	int saveOrder(OrderSaveServiceDto orderSaveServiceDto);
	
	List<MyPageReviewListResult> getMyPageReviewList(MyPageReviewListServiceDto serviceDto);

}
