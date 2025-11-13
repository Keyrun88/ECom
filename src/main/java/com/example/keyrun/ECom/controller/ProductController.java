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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                                                       @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                                                       @RequestParam(name = "orderBy", defaultValue = AppConstants.SORT_DIR,required = false) String orderBy)
    {
        ProductResponse productResponse = productService.getProducts(pageNumber,pageSize,sortBy,orderBy);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
                                                                @RequestParam(name = "orderBy", defaultValue = AppConstants.SORT_DIR,required = false) String orderBy,
                                                                @PathVariable Long categoryId)
    {
        ProductResponse productResponse = productService.searchProductByCategory(pageNumber,pageSize,sortBy,orderBy,categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryID}products")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                @PathVariable Long categoryID)
    {
        ProductDTO savedProduct =  productService.addProduct(productDTO,categoryID);
        return new ResponseEntity<ProductDTO>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                    @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
