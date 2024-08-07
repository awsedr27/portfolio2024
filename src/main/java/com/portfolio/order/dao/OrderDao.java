package com.portfolio.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.order.dto.OrderDto;
import com.portfolio.order.dto.OrderDto.OrderCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderListQuery;
import com.portfolio.order.dto.OrderDto.OrderListResult;

@Mapper
public interface OrderDao {

	List<OrderListResult> selectOrderList(OrderListQuery orderListQuery);

	OrderDto selectOrder(Integer orderId);

	void updateOrderForCancel(OrderCancelQuery orderCancelQuery);

}
