package com.product.controller;

import com.product.model.Product;
import com.product.service.ProductService;
import com.product.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getProducts() {
        logger.info("Fetching products...");
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Data fetched successfully",
                HttpStatus.OK,
                productService.getProducts()
        ));
    }

    @GetMapping("byCategory/{categoryId}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductsByCategory(@PathVariable int categoryId) {
        logger.info("Fetching products by category...");
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Data fetched successfully",
                HttpStatus.OK,
                productService.findByCategoryId(categoryId)
        ));
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@RequestParam String keyword) {
        logger.info("Fetching products by search keyword...");
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Data fetched successfully",
                HttpStatus.OK,
                productService.searchProducts(keyword)
        ));
    }

    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse<Optional<Product>>> findById(@PathVariable int productId) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Data fetched successfully",
                HttpStatus.OK,
                productService.findById(productId)
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Optional<Product>>> saveOrUpdate(@RequestPart Product product, @RequestPart(required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Product saved successfully",
                HttpStatus.OK,
                productService.saveOrUpdate(product,imageFile)
        ));
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable int productId) {
        productService.deleteById(productId);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Request successful",
                "Data deleted successfully",
                HttpStatus.OK,
                null
        ));
    }




}
