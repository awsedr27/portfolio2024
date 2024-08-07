package com.portfolio.order.service;

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
import com.portfolio.order.dto.OrderDto.OrderCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderListQuery;
import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	OrderItemsDao orderItemsDao;

	@Autowired
	UserContext userContext;
	
    @Value("${order.paging.size}")
    private int pagingSize;
    
	@Override
	public List<OrderListResult> getOrderList(OrderListServiceDto orderListServiceDto) {
		OrderListQuery orderListQuery=new OrderListQuery(orderListServiceDto);
		orderListQuery.setUserId(userContext.getUserInfo().getUserId());
		orderListQuery.setLimit(pagingSize);
		List<OrderListResult> list=orderDao.selectOrderList(orderListQuery);
		return list;
	}

	@Override
	public int cancelOrder(OrderCancelServiceDto orderCancelServiceDto) {
		String userId=userContext.getUserInfo().getUserId();
		OrderDto order=orderDao.selectOrder(orderCancelServiceDto.getOrderId());
		if(order==null) {
			throw new CustomException("주문번호가 잘못됬습니다");
		}
		if(!userId.equals(order.getUserId())) {
			 throw new CustomException("잘못된 취소요청입니다(주문자가 아닙니다)");
		}
		if(!CommonEnum.OrderStatus.PENDING.name().equals(order.getStatus())) {
			
			throw new CustomException("취소할 수 없는 상태입니다."+CommonEnum.OrderStatus.PENDING.name());
		}
		OrderCancelQuery orderCancelQuery=new OrderCancelQuery(orderCancelServiceDto);
		orderDao.updateOrderForCancel(orderCancelQuery);
		OrderItemsCancelQuery orderItemsCancelQuery=new OrderItemsCancelQuery(orderCancelServiceDto.getOrderId());
		return orderItemsDao.updateOrderItemsForCancel(orderItemsCancelQuery);
	}

}
