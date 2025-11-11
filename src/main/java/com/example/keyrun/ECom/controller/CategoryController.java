package com.example.keyrun.ECom.controller;

//Import
import com.example.keyrun.ECom.Service.ICategoryService;
import com.example.keyrun.ECom.model.Category;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController
{
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories()
    {
        List<Category> categoryList = null;
        categoryList = categoryService.getAllCategories();
        return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category)
    {
          Category resource = null;
          resource = categoryService.addCategory(category);
          return new ResponseEntity<Category>( resource, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId)
    {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<String>(status, HttpStatus.OK);
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId)
    {
            String status = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<String>(status, HttpStatus.OK);
    }

}
