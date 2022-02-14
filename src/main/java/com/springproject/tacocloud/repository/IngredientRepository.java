package com.springproject.tacocloud.repository;

import com.springproject.tacocloud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient, String> {


}
