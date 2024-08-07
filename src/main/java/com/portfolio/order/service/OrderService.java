package com.portfolio.order.service;

import java.util.List;

import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;

public interface OrderService {

	List<OrderListResult> getOrderList(OrderListServiceDto orderListServiceDto);

	int cancelOrder(OrderCancelServiceDto orderCancelServiceDto);

}
