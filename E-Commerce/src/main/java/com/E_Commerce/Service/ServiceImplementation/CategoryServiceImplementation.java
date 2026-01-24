package com.E_Commerce.Service.ServiceImplementation;

import com.E_Commerce.Model.Category;
import com.E_Commerce.Repository.CategoryRepository;
import com.E_Commerce.Service.ServiceInterface.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplementation implements CategoryService {



        private final CategoryRepository categoryRepository;

        // ADMIN ONLY
        @Override
        @PreAuthorize("hasRole('ADMIN')")
        public Category createCategory(Category category) {

            if (categoryRepository.existsByName(category.getName())) {
                throw new RuntimeException("Category already exists");
            }

            return categoryRepository.save(category);
        }

        // ADMIN ONLY
        @Override
        @PreAuthorize("hasRole('ADMIN')")
        public Category updateCategory(Category category) {

            if (!categoryRepository.existsById(category.getId())) {
                throw new RuntimeException("Category not found");
            }

            return categoryRepository.save(category);
        }

        // ADMIN ONLY
        @Override
        @PreAuthorize("hasRole('ADMIN')")
        public void deleteCategory(Long categoryId) {

            if (!categoryRepository.existsById(categoryId)) {
                throw new RuntimeException("Category not found");
            }

            categoryRepository.deleteById(categoryId);
        }

        // PUBLIC (ADMIN + CUSTOMER)
        @Override
        public List<Category> listCategories() {
            return categoryRepository.findAll();
        }
    }

