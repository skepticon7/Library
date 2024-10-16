package com.youssef.library.cities.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

    private Environment env;

    @Bean
    public Cloudinary cloudinaryBean() {
        String cloudName = env.getProperty("cloudinary.cloud.name");
        String apiKey = env.getProperty("cloudinary.api.key");
        String apiSecret = env.getProperty("cloudinary.api.secret");
        String uploadPreset = env.getProperty("cloudinary.preset.name");
        System.out.println("hey config");
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "upload_preset",uploadPreset,
                "api_key",apiKey,
                "api_secret", apiSecret
        ));
    }
}
