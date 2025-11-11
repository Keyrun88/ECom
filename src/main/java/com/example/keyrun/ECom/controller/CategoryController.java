package com.example.keyrun.ECom.controller;

//Import
import com.example.keyrun.ECom.Service.ICategoryService;
import com.example.keyrun.ECom.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController
{
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.getAllCategories();
            return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<List<Category>>(categoryList, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/public/categories")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        Category resource = null;
        try
        {
            resource = categoryService.addCategory(category);
            return new ResponseEntity<Category>( resource, HttpStatus.CREATED);
        }
        catch (ResponseStatusException e)
        {
            return new ResponseEntity<Category>(resource,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId)
    {
        try {

            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<String>(status, HttpStatus.OK);
        }
        catch (ResponseStatusException exception)
        {
            return new ResponseEntity<String>(exception.getReason(), HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId)
    {
        try {

            String status = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<String>(status, HttpStatus.OK);
        }
        catch (ResponseStatusException exception)
        {
            return new ResponseEntity<String>(exception.getReason(), HttpStatus.NOT_FOUND);
        }

    }

}
