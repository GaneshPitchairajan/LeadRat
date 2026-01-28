package com.E_Commerce.DTO;

import com.E_Commerce.Model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {

    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemResponse> items;

    @Data
    @Builder
    public static class OrderItemResponse {
        private String productName;
        private Integer quantity;
        private Double price;
    }
}
