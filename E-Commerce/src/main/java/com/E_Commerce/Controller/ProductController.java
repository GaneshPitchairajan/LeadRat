package com.E_Commerce.Controller;


import com.E_Commerce.DTO.ProductRequestDTO;
import com.E_Commerce.DTO.ProductResponseDTO;
import com.E_Commerce.Model.Product;
import com.E_Commerce.service.impl.ProductServiceImplementation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImplementation productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }
}
