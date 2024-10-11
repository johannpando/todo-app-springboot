package com.johannpando.todo_app.configuration;

import com.johannpando.todo_app.repository.TodoRepository;
import com.johannpando.todo_app.service.ITodoService;
import com.johannpando.todo_app.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebMvcConfigurer {

    @Autowired
    TodoRepository todoRepository;

    @Bean
    public ITodoService todoService() {
        return new TodoServiceImpl(todoRepository);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
