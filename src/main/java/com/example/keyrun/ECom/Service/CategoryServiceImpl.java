package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.Repository.CategoryRepo;
import com.example.keyrun.ECom.exception.ApiException;
import com.example.keyrun.ECom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl  implements ICategoryService
{
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) throw new ApiException("Category has not been created till now!!");
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category)
    {
        Category existingCategory = categoryRepo.findByname(category.getName());
        if (existingCategory != null) throw new ApiException("Category already exists");
        return categoryRepo.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId)
    {
        Category category = categoryRepo.findById(Math.toIntExact(categoryId))
                            .orElseThrow(() ->  new ApiException("Category not found"));
        categoryRepo.delete(category);
        return  "Deleted Successfully";
    }

    @Override
    public String updateCategory(Category category,Long categoryId)
    {
            Category updateCategory = categoryRepo.findById(Math.toIntExact(categoryId))
                    .orElseThrow(() -> new ApiException("Category not found"));
            updateCategory.setName(category.getName());
            categoryRepo.save(updateCategory);
            return  "Updated Successfully";
    }
}
