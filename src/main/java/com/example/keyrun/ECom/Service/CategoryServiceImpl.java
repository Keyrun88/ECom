package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.Repository.CategoryRepo;
import com.example.keyrun.ECom.exception.ApiException;
import com.example.keyrun.ECom.model.Category;
import com.example.keyrun.ECom.payload.CategoryDTO;
import com.example.keyrun.ECom.payload.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl  implements ICategoryService
{
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
        public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String orderBy)
    {
        Sort sortByAndOrder = orderBy.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        //Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage = categoryRepo.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) throw new ApiException("Category has not been created till now!!");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> {
                    return modelMapper.map(category, CategoryDTO.class);
                }).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(pageDetails.getPageNumber());
        categoryResponse.setPageSize(pageDetails.getPageSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        
        return categoryResponse;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO)
    {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category existingCategory = categoryRepo.findByname(category.getName());
        if (existingCategory != null) throw new ApiException("Category already exists");

        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId)
    {
        Category category = categoryRepo.findById(categoryId)
                            .orElseThrow(() ->  new ApiException("Category not found"));
        categoryRepo.delete(category);
        return  modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category existingCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ApiException("Category not found"));

        existingCategory.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepo.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }


}
