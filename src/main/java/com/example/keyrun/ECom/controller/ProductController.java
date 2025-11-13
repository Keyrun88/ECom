package com.example.keyrun.ECom.controller;

import com.example.keyrun.ECom.Service.IProductService;
import com.example.keyrun.ECom.config.AppConstants;
import com.example.keyrun.ECom.payload.ProductDTO;
import com.example.keyrun.ECom.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController
{
    @Autowired
    private IProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProducts(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                       @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                       @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                       @RequestParam(name = "orderBy", defaultValue = AppConstants.ORDER_BY,required = false) String orderBy)
    {
        ProductResponse productResponse = productService.getProducts(pageNumber,pageSize,sortBy,orderBy);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId)
    {
        ProductResponse productResponse = productService.searchProductByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryID}products")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                @PathVariable Long categoryID)
    {
        ProductDTO savedProduct =  productService.addProduct(productDTO,categoryID);
        return new ResponseEntity<ProductDTO>(savedProduct, HttpStatus.CREATED);
    }

}
