package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.Repository.CategoryRepo;
import com.example.keyrun.ECom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CategoryServiceImpl  implements ICategoryService
{
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category)
    {
        return categoryRepo.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId)
    {
        Category category = categoryRepo.findById(Math.toIntExact(categoryId))
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categoryRepo.delete(category);
        return  "Deleted Successfully";
    }

    @Override
    public String updateCategory(Category category,Long categoryId)
    {
            Category updateCategory = categoryRepo.findById(Math.toIntExact(categoryId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

            updateCategory.setCategoryName(category.getCategoryName());
            categoryRepo.save(updateCategory);
            return  "Updated Successfully";
    }
}
