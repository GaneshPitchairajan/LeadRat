package com.E_Commerce.Service.ServiceImplementation;

import com.E_Commerce.DTO.OrderResponseDTO;
import com.E_Commerce.Model.*;
import com.E_Commerce.Repository.*;
import com.E_Commerce.Service.ServiceInterface.OrderService;
import com.E_Commerce.DTO.PlaceOrderRequestDTO;
import com.E_Commerce.Security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 🛒 CUSTOMER places order
    @Override
    public OrderResponseDTO placeOrder(PlaceOrderRequestDTO request) {

        String username = SecurityUtil.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> orderItems = request.getItems()
                .stream().map(item -> {Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // 🔒 Validate stock
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            // 📉 Reduce stock
            product.setStock(product.getStock() - item.getQuantity());

            return OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .build();

        }).collect(Collectors.toList());

        // 💰 Calculate total using Streams
        double total = orderItems.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.CREATED)
                .totalAmount(total)
                .user(user)
                .orderItems(orderItems)
                .build();

        orderItems.forEach(i -> i.setOrder(order));

        orderRepository.save(order);

        return mapToResponse(order);
    }

    // 👤 CUSTOMER — only own orders
    @Override
    public List<OrderResponseDTO> getMyOrders() {

        String username = SecurityUtil.getCurrentUsername();

        return orderRepository.findByUserUsername(username)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 👑 ADMIN — view all
    @Override
    public List<OrderResponseDTO> getAllOrders() {

        if (!SecurityUtil.hasRole("ADMIN")) {
            throw new AccessDeniedException("Only ADMIN can view all orders");
        }

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 👑 ADMIN — update status
    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {

        if (!SecurityUtil.hasRole("ADMIN")) {
            throw new AccessDeniedException("Only ADMIN can update order status");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));

        return mapToResponse(order);
    }

    // 🔁 Entity → DTO Mapper
    private OrderResponseDTO mapToResponse(Order order) {
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getOrderItems().stream()
                        .map(i -> OrderResponseDTO.OrderItemResponse.builder()
                                .productName(i.getProduct().getName())
                                .quantity(i.getQuantity())
                                .price(i.getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

