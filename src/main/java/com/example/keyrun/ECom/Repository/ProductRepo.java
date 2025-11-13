package com.example.keyrun.ECom.Repository;

import com.example.keyrun.ECom.model.Category;
import com.example.keyrun.ECom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>
{

    Product findByproductName(String productName);
    List<Product> findBycategoryOrderByPriceAsc(Category category);
}
