package com.E_Commerce.DTO;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String name;
    private double price;
    private Integer stock;
    private String categoryName;
}
