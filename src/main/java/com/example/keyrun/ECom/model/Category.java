package com.example.keyrun.ECom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category
{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long CategoryID;
        @NotBlank
        @Size(min = 5,message = "Category name must be contain at least 5 character")
        @Size(max = 20,message = "Category name must not be contain more than 5 character")
        private String name;
}
