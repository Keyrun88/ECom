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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private IFileService fileService;

    @Value("${project.image}")
    private String path;
    @Value("${image.base.url}")
    private String imageBaseUrl;
    @Override
    public ProductResponse getProducts(Integer pageNumber,Integer pageSize,String sortBy,String orderBy)
    {
        Sort sort = orderBy.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepo.findAll(pageable);
        List<Product> products = productPage.getContent();
        if(products.isEmpty()) throw new ApiException("No products found");

        List<ProductDTO> productDTO = products.stream()
                .map(product -> {
                    return modelMapper.map(product, ProductDTO.class);
                }).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pageNumber);
        productResponse.setPageSize(pageSize);
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryID)
    {
        Product product = modelMapper.map(productDTO, Product.class);
        Category category = categoryRepo.findById(categoryID)
                .orElseThrow(() -> new ApiException("Category Not Found"));

        List<Product> existingProduct = category.getProducts();
        for(Product value : existingProduct)
        {
            if(value.getProductName().equals(productDTO.getProductName()))
            {
                throw new ApiException("Product Already Exists");
            }
        }

        product.setCategory(category);
        product.setImage("default.png");
        Double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse searchProductByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,Long categoryId)
    {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ApiException("Category Not Found"));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Product> products = productRepo.findBycategoryOrderByPriceAsc(category,pageDetails);
        List<Product> productList = products.getContent();
        if(productList.isEmpty()) throw new ApiException("No products found by category");

        ProductResponse productResponse = new ProductResponse();
        List<ProductDTO> productDTOList = productList
                .stream()
                .map(product -> {
            return modelMapper.map(product, ProductDTO.class);
        }).toList();

        productResponse.setContent(productDTOList);
        productResponse.setPageNumber(pageDetails.getPageNumber());
        productResponse.setPageSize(pageDetails.getPageSize());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setLastPage(products.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Product> pageProducts = productRepo.findByproductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);

        List<Product> products = pageProducts.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if(products.isEmpty()){
            throw new ApiException("Products not found with keyword: " + keyword);
        }

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDb = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException("Product Not Found"));

        Product product = modelMapper.map(productDTO, Product.class);

        productFromDb.setProductName(product.getProductName());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct = productRepo.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = null;
        product = productRepo.findById(productId)
                        .orElseThrow(() -> new ApiException("Product Not Found"));
        productRepo.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException("Product Not Found"));
        String fileName = fileService.uploadImage(path,image);
        product.setImage(fileName);
        Product updatedProduct = productRepo.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
