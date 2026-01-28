package com.E_Commerce.Service.ServiceInterface;

import com.E_Commerce.DTO.OrderResponseDTO;
import com.E_Commerce.DTO.PlaceOrderRequestDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO placeOrder(PlaceOrderRequestDTO request);

    List<OrderResponseDTO> getMyOrders();

    List<OrderResponseDTO> getAllOrders(); // ADMIN only

    OrderResponseDTO updateOrderStatus(Long orderId, String status); // ADMIN only
}
