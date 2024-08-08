package com.portfolio.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.common.CommonEnum.OrderStatus;
import com.portfolio.exception.CustomException;
import com.portfolio.order.dto.OrderDto.OrderListResult;
import com.portfolio.order.dto.OrderRequest.OrderCancelRequest;
import com.portfolio.order.dto.OrderRequest.OrderItemCancelRequest;
import com.portfolio.order.dto.OrderRequest.OrderListRequest;
import com.portfolio.order.dto.OrderRequest.OrderSaveRequest;
import com.portfolio.order.dto.OrderResponse.OrderListResponse;
import com.portfolio.order.dto.OrderServiceDto.OrderCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemCancelServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderItemSaveServiceDTO;
import com.portfolio.order.dto.OrderServiceDto.OrderListServiceDto;
import com.portfolio.order.dto.OrderServiceDto.OrderSaveServiceDto;
import com.portfolio.order.service.OrderService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
    @PostMapping("/list")
    public ResponseEntity<?> orderList(@Valid @RequestBody OrderListRequest orderListRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		OrderListServiceDto orderListServiceDto=new OrderListServiceDto(orderListRequest);
    		List<OrderListResult> result=orderService.getOrderList(orderListServiceDto);
    		OrderListResponse orderListResponse=new OrderListResponse();
    		orderListResponse.setOrderList(result);
    	    return ResponseEntity.status(HttpStatus.OK).body(orderListResponse);
    	}catch (Exception e) {
    		log.error("주문 목록 불러오기 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 목록 불러오기 실패했습니다");
		}
    
    }
    @PostMapping("/cancel")
    public ResponseEntity<?> orderCancel(@Valid @RequestBody OrderCancelRequest orderCancelRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		OrderCancelServiceDto orderCancelServiceDto=new OrderCancelServiceDto(orderCancelRequest);
    		int result=orderService.cancelOrder(orderCancelServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body("주문을 취소했습니다");
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문 취소에 실패했습니다");
    	}catch (Exception e) {
    		log.error("주문 취소 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 취소 실패했습니다");
		}
    
    }
    @PostMapping("/orderItem/cancel")
    public ResponseEntity<?> orderItemCancel(@Valid @RequestBody OrderItemCancelRequest orderItemCancelRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		OrderItemCancelServiceDto orderItemCancelServiceDto=new OrderItemCancelServiceDto(orderItemCancelRequest);
    		int result=orderService.cancelOrderItem(orderItemCancelServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body("주문 상품을 취소했습니다");
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문 상품 취소에 실패했습니다");
    	}catch (Exception e) {
    		log.error("주문 상품 취소 실패 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 상품 취소 실패했습니다");
		}
    
    }
    //주문
    @PostMapping("/save")
    public ResponseEntity<?> orderSave(@Valid @RequestBody OrderSaveRequest orderSaveRequest,
    		HttpServletResponse httpServletResponse) {
    	try {
    		OrderSaveServiceDto orderSaveServiceDto=new OrderSaveServiceDto();
    		List<OrderItemSaveServiceDTO> saveServiceDto = orderSaveRequest.getOrderSaveList().stream()
                    .map(item -> {
                    	OrderItemSaveServiceDTO serviceItem = new OrderItemSaveServiceDTO();
                        serviceItem.setProductId(item.getProductId());
                        serviceItem.setQuantity(item.getQuantity());
                        return serviceItem;
                    }).collect(Collectors.toList());
    		orderSaveServiceDto.setOrderItems(saveServiceDto);
    		int result = orderService.saveOrder(orderSaveServiceDto);
    	    return ResponseEntity.status(HttpStatus.OK).body("상품 주문을 완료했습니다");
    	}catch(CustomException e) {
    		log.error(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	}catch (Exception e) {
    		log.error("주문을 실패했습니다 "+e.toString());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문을 실패했습니다");
		}
    
    }
    
    
}
