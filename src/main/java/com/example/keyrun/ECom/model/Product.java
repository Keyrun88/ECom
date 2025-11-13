package com.example.keyrun.ECom.model;

import com.example.keyrun.ECom.payload.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productID;
    @NotBlank
    @Size(min = 3, message = "Product name should be greater than 3 character")
    private String productName;
    @NotBlank
    @Size(min = 6, message = "Product description should be greater than 6 character")
    private String productDescription;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;
}
