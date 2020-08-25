package com.geek.employee;

import com.geek.common.utils.IdWorker;
import com.geek.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.geek")// Spring Boot 包扫描。
@EntityScan(value = "com.geek.domain.employee")// jpa 注解扫描。
public class EmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtils jwtUtil() {
        return new JwtUtils();
    }
}
