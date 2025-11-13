package com.example.keyrun.ECom.Repository;

import com.example.keyrun.ECom.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>
{

    Category findByname(@NotBlank @Size(min = 5,message = "Category name must be contain at least 5 character") @Size(max = 20,message = "Category name must not be contain more than 5 character") String name);

    Category findBycategoryID(Long categoryID);

}
