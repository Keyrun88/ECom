package com.example.keyrun.ECom.Service;

import com.example.keyrun.ECom.payload.ProductDTO;
import com.example.keyrun.ECom.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IProductService
{

    ProductResponse getProducts(Integer pageNumber,Integer pageSize,String sortBy,String orderby);

    ProductDTO addProduct(@Valid ProductDTO productDTO, Long categoryID);

    ProductResponse searchProductByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,Long categoryId);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, @Valid ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
