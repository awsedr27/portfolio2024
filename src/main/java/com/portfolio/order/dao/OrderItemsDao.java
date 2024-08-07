package com.portfolio.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.order.dto.OrderDto.OrderItemsCancelQuery;

@Mapper
public interface OrderItemsDao {

	int updateOrderItemsForCancel(OrderItemsCancelQuery orderItemsCancelQuery);

}
