package com.portfolio.order.dto;

import java.sql.Timestamp;

import com.portfolio.order.dto.OrderRequest.OrderCancelRequest;
import com.portfolio.order.dto.OrderRequest.OrderListRequest;

import lombok.Getter;
import lombok.Setter;

public class OrderServiceDto {
	
    @Getter
    @Setter
	public static class OrderListServiceDto {
    	private String status;
        private Timestamp createDate;
        public OrderListServiceDto() {
        	
        }
		public OrderListServiceDto(OrderListRequest orderListRequest) {
			this.status=orderListRequest.getStatus();
			this.createDate=orderListRequest.getCreateDate();
		}

	}
    @Getter
    @Setter
	public static class OrderCancelServiceDto {
    	private Integer orderId;
    	public OrderCancelServiceDto() {
    		
    	}
		public OrderCancelServiceDto(OrderCancelRequest orderCancelRequest) {
			this.orderId=orderCancelRequest.getOrderId();
		}

	}
}
