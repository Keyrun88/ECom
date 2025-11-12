package com.example.keyrun.ECom.controller;

//Import
import com.example.keyrun.ECom.Service.ICategoryService;
import com.example.keyrun.ECom.config.AppConstants;
import com.example.keyrun.ECom.model.Category;
import com.example.keyrun.ECom.payload.CategoryDTO;
import com.example.keyrun.ECom.payload.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                             @RequestParam(name = "orderBy", defaultValue = AppConstants.ORDER_BY,required = false) String orderBy)
    {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize,sortBy,orderBy);
        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO)
    {
          CategoryDTO resource = null;
          resource = categoryService.addCategory(categoryDTO);
          return new ResponseEntity<CategoryDTO>( resource, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId)
    {
        CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId)
    {
        CategoryDTO savedCategoryDto =  categoryService.updateCategory(categoryDTO,categoryId);
        return new ResponseEntity<CategoryDTO>(savedCategoryDto, HttpStatus.OK);
    }

}
