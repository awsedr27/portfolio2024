package com.portfolio.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.order.dto.OrderDto.OrderItemCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemDto;
import com.portfolio.order.dto.OrderDto.MyPageReviewListQuery;
import com.portfolio.order.dto.OrderDto.MyPageReviewListResult;
import com.portfolio.order.dto.OrderDto.OrderItemsCancelByOrderIdQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsSaveQuery;


@Mapper
public interface OrderItemsDao {

	int updateOrderItemsForCancelByOrderId(OrderItemsCancelByOrderIdQuery orderItemsCancelQuery);

	OrderItemDto selectOrderItem(Integer orderItemId);
	
	int updateOrderItemForCancel(OrderItemCancelQuery orderItemCancelQuery);

	int insertOrderItemsList(OrderItemsSaveQuery orderItemsSaveQuery);

	List<MyPageReviewListResult> selectMyPageReviewList(MyPageReviewListQuery orderItemReviewListQuery);
	
	List<OrderItemDto> selectOrderItemsByOrderId(Integer orderId);



}
