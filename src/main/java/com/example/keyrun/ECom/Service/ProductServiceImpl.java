package com.example.keyrun.ECom.Service;


import com.example.keyrun.ECom.Repository.CategoryRepo;
import com.example.keyrun.ECom.Repository.ProductRepo;
import com.example.keyrun.ECom.exception.ApiException;
import com.example.keyrun.ECom.model.Category;
import com.example.keyrun.ECom.model.Product;
import com.example.keyrun.ECom.payload.ProductDTO;
import com.example.keyrun.ECom.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService
{
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponse getProducts(Integer pageNumber,Integer pageSize,String sortBy,String orderBy)
    {

        ProductResponse productResponse = new ProductResponse();
        List<Product> products = productRepo.findAll();
        if(products.isEmpty()) throw new ApiException("No products found");

        List<ProductDTO> productDTO = products.stream()
                .map(product -> {
                    return modelMapper.map(product, ProductDTO.class);
                }).toList();
        productResponse.setContent(productDTO);
        return productResponse;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryID)
    {
        Product existingProduct = productRepo.findByproductName(productDTO.getProductName());
        if(existingProduct != null) throw new ApiException("Product Already Exists");

        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepo.findById(categoryID)
                .orElseThrow(() -> new ApiException("Category Not Found"));

        product.setCategory(category);
        product.setImage("default.png");
        Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse searchProductByCategory(Long categoryId)
    {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ApiException("Category Not Found"));

        List<Product> products = productRepo.findBycategoryOrderByPriceAsc(category);
        if(products.isEmpty()) throw new ApiException("No products found by category");

        ProductResponse productResponse = new ProductResponse();
        List<ProductDTO> productDTOList = products
                .stream()
                .map(product -> {
            return modelMapper.map(product, ProductDTO.class);
        }).toList();

        productResponse.setContent(productDTOList);
        return productResponse;
    }
}
