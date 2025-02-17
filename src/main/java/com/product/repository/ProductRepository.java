package com.product.repository;

import com.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
//    List<Product> findByCategoryId(@Param("categoryId") Integer id);


    List<Product> findByCategoryId(Integer id);

//    List<Product> findByBrand(String brand);

//    @Query("SELECT p FROM Product p WHERE p.brand = :brand")
//    List<Product> findByBrand(@Param("brand") String brand);

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) = LOWER(:brand)")
    List<Product> findByBrand(@Param("brand") String brand);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(String keyword);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.id = :id")
    Optional<Product> findByIdWithCategory(@Param("id") int id);
}
