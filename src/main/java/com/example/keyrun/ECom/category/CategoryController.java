package com.example.keyrun.ECom.category;

//Import
import com.example.keyrun.ECom.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CategoryController
{
    List<Category> categories = new ArrayList<>();

    @GetMapping("/categories")
    public List<Category> getAllCategories()
    {
        categories.clear();
        categories.add(new Category(1,"Category 1"));
        categories.add(new Category(2,"Category 2"));
        categories.add(new Category(3,"Category 3"));
        return categories;
    }
}
