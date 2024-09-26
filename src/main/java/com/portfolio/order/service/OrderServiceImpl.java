package com.portfolio.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portfolio.cart.dao.CartDao;
import com.portfolio.cart.dto.CartDto.CartListDeleteQuery;
import com.portfolio.common.CommonEnum;
import com.portfolio.common.UserContext;
import com.portfolio.exception.CustomException;
import com.portfolio.order.dao.OrderDao;
import com.portfolio.order.dao.OrderItemsDao;
import com.portfolio.order.dto.OrderDto;
import com.portfolio.order.dto.OrderDto.OrderCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemCancelQuery;
import com.portfolio.order.dto.OrderDto.OrderItemDto;
import com.portfolio.order.dto.OrderDto.MyPageReviewListQuery;
import com.portfolio.order.dto.OrderDto.MyPageReviewListResult;
import com.portfolio.order.dto.OrderDto.OrderItemSaveQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsCancelByOrderIdQuery;
import com.portfolio.order.dto.OrderDto.OrderItemsSaveQuery;
import com.portfolio.order.dto.OrderDto.OrderListQuery;
import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderDto.OrderSaveQuery;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.MyPageReviewListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemSaveServiceDTO;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderSaveServiceDto;
import com.portfolio.product.dao.ProductDao;
import com.portfolio.product.dto.ProductDto.Product;
import com.portfolio.product.dto.ProductDto.ProductUpdateQuantityQuery;

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
	
	@Autowired
	CartDao cartDao;
	
    @Value("${order.paging.size}")
    private int pagingSize;
    
    @Value("${mypage.review.paging.size}")
    private int myPageReviewPagingSize;
    
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
		List<ProductUpdateQuantityQuery> productList=new ArrayList<ProductUpdateQuantityQuery>();
		OrderDto order=orderDao.selectOrderWithExclusiveLock(orderCancelServiceDto.getOrderId());
		if(order==null) {
			throw new CustomException("주문번호가 잘못됬습니다");
		}
		List<OrderItemDto> orderItems=orderItemsDao.selectOrderItemsWithExclusiveLockByOrderId(orderCancelServiceDto.getOrderId());
		if(orderItems==null||orderItems.size()==0) {
			throw new CustomException("주문상품이 없습니다");
		}
		if(!userId.equals(order.getUserId())) {
			 throw new CustomException("잘못된 취소요청입니다(주문자가 아닙니다)");
		}
		if(!CommonEnum.OrderStatus.PENDING.name().equals(order.getStatus())) {
			throw new CustomException("취소할 수 없는 상태입니다.");
		}
		for(OrderItemDto orderItem:orderItems) {
			if(!CommonEnum.OrderItemStatus.CANCELLED.name().equals(orderItem.getStatus())
					&&!CommonEnum.OrderItemStatus.PENDING.name().equals(orderItem.getStatus())) {
				throw new CustomException("주문 아이템 중에 취소할 수 없는 상태의 아이템이 있습니다");
			}
			productList.add(new ProductUpdateQuantityQuery(orderItem.getProductId(),orderItem.getQuantity()));
		}
		OrderCancelQuery orderCancelQuery=new OrderCancelQuery(orderCancelServiceDto);
		orderDao.updateOrderForCancel(orderCancelQuery);
		OrderItemsCancelByOrderIdQuery orderItemsCancelByOrderIdQuery=new OrderItemsCancelByOrderIdQuery(orderCancelServiceDto.getOrderId());
		orderItemsDao.updateOrderItemsForCancelByOrderId(orderItemsCancelByOrderIdQuery);
		
		return productDao.updateQuantitiesPlus(productList);
	}

	@Override
	public int cancelOrderItem(OrderItemCancelServiceDto orderItemCancelServiceDto) {
		String userId=userContext.getUserInfo().getUserId();
		boolean orderItemIdCheckFlag=false;
		boolean orderCancelFlag=true;
		List<ProductUpdateQuantityQuery> productList=new ArrayList<ProductUpdateQuantityQuery>();
		OrderDto order=orderDao.selectOrderWithExclusiveLock(orderItemCancelServiceDto.getOrderId());
		if(order==null) {
			throw new CustomException("주문번호가 잘못됬습니다");
		}
		List<OrderItemDto> orderItems=orderItemsDao.selectOrderItemsWithExclusiveLockByOrderId(orderItemCancelServiceDto.getOrderId());
		if(orderItems==null||orderItems.size()==0) {
			throw new CustomException("주문상품이 없습니다");
		}
		for(OrderItemDto orderItem:orderItems) {
			if(orderItem.getOrderItemId().equals(orderItemCancelServiceDto.getOrderItemId())) {
				//삭제요청한 주문상품인 경우
				if(!CommonEnum.OrderItemStatus.PENDING.name().equals(orderItem.getStatus())) {
					throw new CustomException("취소할 수 없는 상태입니다.");
				}
				orderItemIdCheckFlag=true;
				productList.add(new ProductUpdateQuantityQuery(orderItem.getProductId(),orderItem.getQuantity()));
			}else {
				//삭제요청한 주문상품이 아닌경우
				if(!CommonEnum.OrderItemStatus.CANCELLED.name().equals(orderItem.getStatus())) {
					orderCancelFlag=false;
				}
			}
		}
		if(!orderItemIdCheckFlag) {
			throw new CustomException("삭제 요청한 주문상품번호와 주문번호가 매칭이 안됩니다");
		}
		if(!userId.equals(order.getUserId())) {
			 throw new CustomException("잘못된 취소요청입니다(주문자가 아닙니다)");
		}
		
		OrderItemCancelQuery orderItemCancelQuery =new OrderItemCancelQuery(orderItemCancelServiceDto.getOrderItemId());
		orderItemsDao.updateOrderItemForCancel(orderItemCancelQuery);
		if(orderCancelFlag) {
			//주문상품이 전부 취소상태일 때 주문도 취소상태로 변경
			OrderCancelQuery orderCancelQuery=new OrderCancelQuery();
			orderCancelQuery.setOrderId(orderItemCancelServiceDto.getOrderId());
			orderDao.updateOrderForCancel(orderCancelQuery);
		}
		return productDao.updateQuantitiesPlus(productList);
	}

	@Override
	public int saveOrder(OrderSaveServiceDto orderSaveServiceDto) {
		//상품과 상품수량이 남아있는지 검색
		int totalPrice=0;
		//배달비 고정->거리에따른 배달비기획으로 고도화가능
		int deliveryPrice=4000;
		List<Integer> productIdList = orderSaveServiceDto.getOrderItems().stream()
                .map(OrderItemSaveServiceDTO::getProductId)
                .collect(Collectors.toList());
    	List<Product> productList=productDao.selectProductListWithExclusiveLock(productIdList);
		String userId=userContext.getUserInfo().getUserId();
		List<ProductUpdateQuantityQuery> quantityUpdateQuery=new ArrayList<ProductUpdateQuantityQuery>();
		List<OrderItemSaveQuery> orderItemSaveQuery = new ArrayList<>();
		
		for (OrderItemSaveServiceDTO orderItem : orderSaveServiceDto.getOrderItems()) {
		    Product matchingProduct = productList.stream()
		        .filter(product -> product.getProductId().equals(orderItem.getProductId()))
		        .findFirst()
		        .orElse(null);
		    
		    if (matchingProduct != null) {
	        	if("N".equals(matchingProduct.getUseYn())) {
	        		throw new CustomException("현재 판매가 중지된 상품입니다");
	        	}
		        if (orderItem.getQuantity() > matchingProduct.getQuantity()) {
		        	throw new CustomException("주문 수량이 부족합니다");
		        }
		        quantityUpdateQuery.add(new ProductUpdateQuantityQuery(orderItem.getProductId(), orderItem.getQuantity()));
	        	orderItemSaveQuery.add(new OrderItemSaveQuery(orderItem.getProductId(),orderItem.getQuantity(),matchingProduct.getPrice()));
	        	totalPrice+=(matchingProduct.getPrice()*orderItem.getQuantity());
		    } else {
		    	throw new CustomException("존재하지 않는 상품입니다");
		    }
		}
		productDao.updateQuantitiesMinus(quantityUpdateQuery);

		OrderSaveQuery orderSaveQuery=new OrderSaveQuery();
		orderSaveQuery.setTotalPrice(totalPrice+deliveryPrice);
		orderSaveQuery.setStatus(CommonEnum.OrderStatus.PENDING.name());
		orderSaveQuery.setUserId(userId);
		orderSaveQuery.setPostcode(orderSaveServiceDto.getPostcode());
		orderSaveQuery.setRoadAddress(orderSaveServiceDto.getRoadAddress());
		orderSaveQuery.setJibunAddress(orderSaveServiceDto.getJibunAddress());
		orderSaveQuery.setDetailAddress(orderSaveServiceDto.getDetailAddress());
		orderDao.insertOrder(orderSaveQuery);
		
		OrderItemsSaveQuery orderItemsSaveQuery=new OrderItemsSaveQuery();
        orderItemsSaveQuery.setOrderItems(orderItemSaveQuery);
        orderItemsSaveQuery.setStatus(CommonEnum.OrderItemStatus.PENDING.name());
        orderItemsSaveQuery.setOrderId(orderSaveQuery.getOrderId());
        orderItemsDao.insertOrderItemsList(orderItemsSaveQuery);
        
        CartListDeleteQuery CartListDeleteQuery=new CartListDeleteQuery();
        CartListDeleteQuery.setUserId(userId);
        CartListDeleteQuery.setProductIdList(productIdList);
        return cartDao.deleteCartList(CartListDeleteQuery);
	}

	@Override
	public List<MyPageReviewListResult> getMyPageReviewList(MyPageReviewListServiceDto serviceDto) {
		String userId=userContext.getUserInfo().getUserId();
		MyPageReviewListQuery myPageReviewListQuery=new MyPageReviewListQuery(serviceDto);
		myPageReviewListQuery.setUserId(userId);
		myPageReviewListQuery.setOrderItemStatus(CommonEnum.OrderItemStatus.COMPLETED.name());
		myPageReviewListQuery.setLimit(myPageReviewPagingSize);
		return orderItemsDao.selectMyPageReviewList(myPageReviewListQuery);
	}



}
