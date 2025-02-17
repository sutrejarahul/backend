package com.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.product.model.Product;
import com.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final  ProductRepository productRepository;
    private final Cloudinary cloudinary;
    @Autowired
    public ProductService(ProductRepository productRepository, Cloudinary cloudinary) {
        this.productRepository = productRepository;
        this.cloudinary = cloudinary;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Integer id) {
        logger.info("Fetching products by category ID: {}", id);  // 🔥 Logging API Call
        List<Product> list = productRepository.findByCategoryId(id);
        if(!list.isEmpty()) {
            return list;
        }
        logger.warn("Products not found for category ID: {}", id);
        return list;
    }

    public List<Product> findByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }


    public Optional<Product> findById(int productId) {
        logger.info("Fetching product by ID: {}", productId);
        Optional<Product> product = productRepository.findByIdWithCategory(productId);
        if(product.isPresent()) {
            logger.info("Product found for ID: {}", productId);
            return product;
        }
        logger.warn("Product not found for ID: {}", productId);
        return Optional.empty();
    }

    public Optional<Product> saveOrUpdate(Product product, MultipartFile imageFile) throws IOException {
        if(imageFile != null) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            var uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),
                    ObjectUtils.asMap("folder", "products"));
            product.setImageUrl(uploadResult.get("secure_url").toString());
        }
        Product savedProduct = productRepository.save(product);
        return productRepository.findById(savedProduct.getId());
    }

    public void deleteById(int productId) {
        productRepository.deleteById(productId);
    }
}
