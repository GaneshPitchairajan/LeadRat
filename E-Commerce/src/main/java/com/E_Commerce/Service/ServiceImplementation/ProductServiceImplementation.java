package com.E_Commerce.service.impl;

import com.E_Commerce.DTO.ProductRequestDTO;
import com.E_Commerce.DTO.ProductResponseDTO;
import com.E_Commerce.exception.ResourceNotFoundException;
import com.E_Commerce.exception.UnauthorizedException;
import com.E_Commerce.Model.Category;
import com.E_Commerce.Model.Product;
import com.E_Commerce.Repository.CategoryRepository;
import com.E_Commerce.Repository.ProductRepository;
import com.E_Commerce.Security.SecurityUtil;
import com.E_Commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // ================= ADMIN ONLY =================

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {
        validateAdminRole();

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO request) {
        validateAdminRole();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Override
    public void deleteProduct(Long productId) {
        validateAdminRole();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    // ================= PUBLIC =================

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= HELPERS =================

    private void validateAdminRole() {
        if (!SecurityUtil.hasRole("ROLE_ADMIN")) {
            throw new UnauthorizedException("Admin access required");
        }
    }

    private ProductResponseDTO mapToResponse(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
