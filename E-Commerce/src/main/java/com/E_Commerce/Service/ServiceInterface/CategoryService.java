package com.E_Commerce.Service.ServiceInterface;

import com.E_Commerce.Model.Category;
import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryId);

    List<Category> listCategories();
}

