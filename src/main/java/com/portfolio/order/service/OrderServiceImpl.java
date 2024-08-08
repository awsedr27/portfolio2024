package com.portfolio.order.service;

import java.util.ArrayList;
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
import com.portfolio.order.dto.OrderDto.OrderItemCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemDto;
import com.portfolio.order.dto.OrderDto.OrderItemSaveQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsCancelByOrderIdQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsSaveQuery;
import com.portfolio.order.dto.OrderDto.OrderListQuery;
import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderDto.OrderSaveQuery;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemSaveServiceDTO;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderSaveServiceDto;
import com.portfolio.product.dao.ProductDao;
import com.portfolio.product.dto.ProductDto.Product;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	OrderItemsDao orderItemsDao;

	@Autowired
	UserContext userContext;
	
	@Autowired
	ProductDao productDao;
	
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
			
			throw new CustomException("취소할 수 없는 상태입니다.");
		}
		OrderCancelQuery orderCancelQuery=new OrderCancelQuery(orderCancelServiceDto);
		orderDao.updateOrderForCancel(orderCancelQuery);
		OrderItemsCancelByOrderIdQuery orderItemsCancelByOrderIdQuery=new OrderItemsCancelByOrderIdQuery(orderCancelServiceDto.getOrderId());
		return orderItemsDao.updateOrderItemsForCancelByOrderId(orderItemsCancelByOrderIdQuery);
	}

	@Override
	public int cancelOrderItem(OrderItemCancelServiceDto orderItemCancelServiceDto) {
		String userId=userContext.getUserInfo().getUserId();
		OrderItemDto OrderItemDto=orderItemsDao.selectOrderItem(orderItemCancelServiceDto.getOrderItemId());
		if(OrderItemDto==null) {
			throw new CustomException("주문상품번호가 잘못됬습니다");
		}
		OrderDto order=orderDao.selectOrder(OrderItemDto.getOrderId());
		if(order==null) {
			throw new CustomException("주문번호가 잘못됬습니다");
		}
		if(!userId.equals(order.getUserId())) {
			 throw new CustomException("잘못된 취소요청입니다(주문자가 아닙니다)");
		}
		if(!CommonEnum.OrderItemStatus.PENDING.name().equals(OrderItemDto.getStatus())) {
			
			throw new CustomException("취소할 수 없는 상태입니다.");
		}
		
		OrderItemCancelQuery orderItemCancelQuery =new OrderItemCancelQuery(orderItemCancelServiceDto.getOrderItemId());
		
		return orderItemsDao.updateOrderItemForCancel(orderItemCancelQuery);
	}

	@Override
	public int saveOrder(OrderSaveServiceDto orderSaveServiceDto) {
		//상품과 상품수량이 남아있는지 검색
		int totalPrice=0;
		OrderItemsSaveQuery orderItemsSaveQuery=new OrderItemsSaveQuery();
		List<OrderItemSaveQuery> orderItemSaveQuery = new ArrayList<>();
		String userId=userContext.getUserInfo().getUserId();
        for (OrderItemSaveServiceDTO item : orderSaveServiceDto.getOrderItems()) {
        	Product product=productDao.selectProductWithExclusiveLock(item.getProductId());
        	if(product==null) {
        		throw new CustomException("존재하지 않는 상품입니다");
        	}
        	if("N".equals(product.getUseYn())) {
        		throw new CustomException("현재 판매가 중지된 상품입니다");
        	}
        	if(item.getQuantity()>product.getQuantity()) {
        		throw new CustomException("주문 수량이 부족합니다");
        	}
        	orderItemSaveQuery.add(new OrderItemSaveQuery(product.getProductId(),item.getQuantity(),product.getPrice()));
        	totalPrice+=product.getPrice();
        }
		OrderSaveQuery orderSaveQuery=new OrderSaveQuery();
		orderSaveQuery.setTotalPrice(totalPrice);
		orderSaveQuery.setStatus(CommonEnum.OrderStatus.PENDING.name());
		orderSaveQuery.setUserId(userId);
		orderDao.insertOrder(orderSaveQuery);
		
        orderItemsSaveQuery.setOrderItems(orderItemSaveQuery);
        orderItemsSaveQuery.setStatus(CommonEnum.OrderItemStatus.PENDING.name());
        orderItemsSaveQuery.setOrderId(orderSaveQuery.getOrderId());
		return orderItemsDao.insertOrderItemsList(orderItemsSaveQuery);
	}



}
