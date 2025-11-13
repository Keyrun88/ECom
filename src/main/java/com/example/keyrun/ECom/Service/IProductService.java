package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.payload.ProductDTO;
import com.example.keyrun.ECom.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface IProductService
{

    ProductResponse getProducts(Integer pageNumber,Integer pageSize,String sortBy,String orderby);

    ProductDTO addProduct(@Valid ProductDTO productDTO, Long categoryID);

    ProductResponse searchProductByCategory(Long categoryId);
}
