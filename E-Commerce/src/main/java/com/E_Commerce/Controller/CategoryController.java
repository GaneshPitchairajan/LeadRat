package com.E_Commerce.Controller;

import com.E_Commerce.Model.Category;
import com.E_Commerce.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create a new category (ADMIN only, enforced in service)
    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // Update a category (ADMIN only, enforced in service)
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        return categoryService.updateCategory(category);
    }

    // Delete a category (ADMIN only, enforced in service)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    // List all categories (everyone can access)
    @GetMapping
    public List<Category> list() {
        return categoryService.listCategories();
    }
}
