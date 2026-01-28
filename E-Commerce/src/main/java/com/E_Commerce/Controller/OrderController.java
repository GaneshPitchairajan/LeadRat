package com.E_Commerce.Controller;

import com.E_Commerce.Service.ServiceInterface.OrderService;
import com.E_Commerce.DTO.OrderResponseDTO;
import com.E_Commerce.DTO.PlaceOrderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 🛒 CUSTOMER — Place Order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody PlaceOrderRequestDTO request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    // 👤 CUSTOMER — View Own Orders
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    // 👑 ADMIN — View All Orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 👑 ADMIN — Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
