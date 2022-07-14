package com.cloud.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        ConnectionPool connectionPool = new ConnectionPool(30, 5L, TimeUnit.MINUTES);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectionPool(connectionPool).build();
        ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
}
