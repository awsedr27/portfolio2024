package com.portfolio.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.order.dto.OrderDto.OrderItemCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemDto;
import com.portfolio.order.dto.OrderDto.OrderItemsCancelByOrderIdQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsSaveQuery;

@Mapper
public interface OrderItemsDao {

	int updateOrderItemsForCancelByOrderId(OrderItemsCancelByOrderIdQuery orderItemsCancelQuery);

	OrderItemDto selectOrderItem(Integer orderItemId);
	
	int updateOrderItemForCancel(OrderItemCancelQuery orderItemCancelQuery);

	int insertOrderItemsList(OrderItemsSaveQuery orderItemsSaveQuery);


}
