package com.product.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.product.util.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", Constant.CLOUD_NAME,
                "api_key", Constant.API_KEY,
                "api_secret", Constant.API_SECRET,
                "secure", Boolean.TRUE
        ));
    }
}

