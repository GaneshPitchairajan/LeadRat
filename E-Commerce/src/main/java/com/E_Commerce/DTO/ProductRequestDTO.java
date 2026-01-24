package com.E_Commerce.DTO;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String name;
    private double price;
    private Integer stock;
    private Long categoryId;
}

