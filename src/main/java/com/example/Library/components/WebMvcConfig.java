package com.example.Library.components;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Показываем Spring'у, что если кто-то обращается по URL /uploads/...
        // то надо искать файлы в этой папке на диске
        registry.addResourceHandler("C:\\Users\\Администратор\\IdeaProjects\\Library\\src\\main\\java\\com\\example\\Library\\uploads\\**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    }
}
