package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.model.Category;
import com.example.keyrun.ECom.payload.CategoryDTO;
import com.example.keyrun.ECom.payload.CategoryResponse;

import java.util.List;

public interface ICategoryService
{
     CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String orderBy);
     CategoryDTO addCategory(CategoryDTO categoryDTO);
     CategoryDTO deleteCategory(Long categoryId);
     CategoryDTO updateCategory(CategoryDTO categoryDTO,Long categoryId);
}

