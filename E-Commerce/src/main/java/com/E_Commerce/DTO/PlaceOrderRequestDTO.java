package com.E_Commerce.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequestDTO {

    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}
