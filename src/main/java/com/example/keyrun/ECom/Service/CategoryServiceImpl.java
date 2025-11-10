package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl  implements ICategoryService
{
    List<Category> categoryList = new ArrayList<>();
    Long categoryId = 0L;
    @Override
    public List<Category> getAllCategories() {
        return categoryList;
    }

    @Override
    public Category addCategory(Category category)
    {
        category.setCategoryID(++categoryId);
        categoryList.add(category);
        return category;
    }

    @Override
    public String deleteCategory(Long categoryId)
    {

        Category category = categoryList.stream()
                .filter(c -> c.getCategoryID() == categoryId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categoryList.remove(category);
        return  "Deleted Successfully";
    }

    @Override
    public String updateCategory(Category category,Long categoryId)
    {
        Optional<Category> categoryOptional = categoryList.stream()
                .filter(c -> c.getCategoryID() == categoryId)
                .findFirst();

        if(categoryOptional.isPresent())
        {
            Category categoryToUpdate = categoryOptional.get();
            categoryToUpdate.setCategoryName(category.getCategoryName());
            return  "Updated Successfully";
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }

}
