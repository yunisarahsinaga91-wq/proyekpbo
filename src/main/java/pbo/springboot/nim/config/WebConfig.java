package pbo.springboot.nim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Path absolut ke folder images
        String uploadPath = System.getProperty("user.dir")
                .replace("\\", "/")          // fix Windows backslash
                + "/src/main/resources/static/images/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                    "classpath:/static/images/",   // dari classpath dulu (JAR)
                    "file:" + uploadPath            // fallback dari filesystem (dev)
                );
    }
}