package com.E_Commerce.service;

import com.E_Commerce.DTO.ProductRequestDTO;
import com.E_Commerce.DTO.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    // ---------- ADMIN ONLY ----------
    ProductResponseDTO createProduct(ProductRequestDTO request);

    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO request);

    void deleteProduct(Long productId);

    // ---------- PUBLIC ----------
    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> getProductsByCategory(Long categoryId);
}
