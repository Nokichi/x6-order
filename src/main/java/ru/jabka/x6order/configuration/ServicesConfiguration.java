package ru.jabka.x6order.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Setter
@Configuration
@ConfigurationProperties("services")
public class ServicesConfiguration {

    private String userServiceUrl;
    private String productServiceUrl;

    @Bean
    public RestTemplate userService() {
        return initService(userServiceUrl);
    }

    @Bean
    public RestTemplate productService() {
        return initService(productServiceUrl);
    }

    private RestTemplate initService(String url) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url));
        return restTemplate;
    }
}