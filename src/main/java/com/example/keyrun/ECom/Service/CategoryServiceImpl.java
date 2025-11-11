package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.Repository.CategoryRepo;
import com.example.keyrun.ECom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

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
        //categoryRepo.save(category);
        //return category;
        return categoryRepo.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId)
    {

        Category category = categoryRepo.findAll()
                            .stream()
                            .filter(c -> c.getCategoryID() == categoryId)
                            .findFirst()
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categoryRepo.delete(category);
        return  "Deleted Successfully";
    }

    @Override
    public String updateCategory(Category category,Long categoryId)
    {
        Optional<Category> categoryOptional = categoryRepo.findAll().stream()
                .filter(c -> c.getCategoryID() == categoryId)
                .findFirst();

        if(categoryOptional.isPresent())
        {
            Category categoryToUpdate = categoryOptional.get();
            categoryToUpdate.setCategoryName(category.getCategoryName());
            categoryRepo.save(categoryToUpdate);
            return  "Updated Successfully";
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }

}
