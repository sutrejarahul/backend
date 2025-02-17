package com.product.service;

import com.product.model.Product;
import com.product.repository.ProductRepository;
import com.product.util.ImageUtil;
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

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            product.setImageBase64(ImageUtil.encodeImage(product.getImageData()));
        });
        return products;
    }

    public List<Product> findByCategoryId(Integer id) {
        logger.info("Fetching products by category ID: {}", id);  // 🔥 Logging API Call
        List<Product> list = productRepository.findByCategoryId(id);
        if(!list.isEmpty()) {
            list.forEach(product -> {
                product.setImageBase64(ImageUtil.encodeImage(product.getImageData()));
            });
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
            product.get().setImageBase64(ImageUtil.encodeImage(product.get().getImageData()));
            logger.info("Product found for ID: {}", productId);
            return product;
        }
        logger.warn("Product not found for ID: {}", productId);
        return Optional.empty();
    }

    public Optional<Product> saveOrUpdate(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        System.out.println(product.toString());
        
        Product savedProduct = productRepository.save(product);
        return productRepository.findById(savedProduct.getId());
    }

    public void deleteById(int productId) {
        productRepository.deleteById(productId);
    }
}
