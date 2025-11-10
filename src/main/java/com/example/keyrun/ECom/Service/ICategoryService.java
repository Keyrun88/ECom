package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.model.Category;
import java.util.List;

public interface ICategoryService
{
     List<Category> getAllCategories();
     Category addCategory(Category category);
     String deleteCategory(Long categoryId);

    String updateCategory(Category category,Long categoryId);
}

