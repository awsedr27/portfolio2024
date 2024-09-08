package com.portfolio.order.dto;

import java.sql.Timestamp;
import java.util.List;

import com.portfolio.order.dto.OrderRequest.OrderCancelRequest;
import com.portfolio.order.dto.OrderRequest.OrderItemCancelRequest;
import com.portfolio.order.dto.OrderRequest.MyPageReviewListRequest;
import com.portfolio.order.dto.OrderRequest.OrderItemSaveRequest;
import com.portfolio.order.dto.OrderRequest.OrderListRequest;
import com.portfolio.order.dto.OrderRequest.OrderSaveRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class OrderServiceDto {
	
    @Getter
    @Setter
	public static class OrderListServiceDto {
    	private String status;
    	private Integer orderId;
        public OrderListServiceDto() {
        	
        }
		public OrderListServiceDto(OrderListRequest orderListRequest) {
			this.status=orderListRequest.getStatus();
			this.orderId=orderListRequest.getOrderId();
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
    @Getter
    @Setter
	public static class OrderItemCancelServiceDto {
    	private Integer orderItemId;
    	public OrderItemCancelServiceDto() {
    		
    	}
		public OrderItemCancelServiceDto(OrderItemCancelRequest orderItemsCancelRequest) {
			this.orderItemId=orderItemsCancelRequest.getOrderItemId();
		}

	}
    @Getter
    @Setter
	public static class OrderSaveServiceDto {
        private List<OrderItemSaveServiceDTO> orderItems;
        private String postcode;
        private String roadAddress;
        private String jibunAddress;
        private String detailAddress;
	}
    @Getter
    @Setter
    public static class OrderItemSaveServiceDTO {
        private Integer productId;
        private Integer quantity;
    }
    @Getter
    @Setter
    public static class MyPageReviewListServiceDto {
    	public MyPageReviewListServiceDto() {
    		
    	}
        public MyPageReviewListServiceDto(MyPageReviewListRequest request) {
			this.orderItemId=request.getOrderItemId();
			this.type=request.getType();
		}
		private String type;
        private Integer orderItemId;
    }
    
}
