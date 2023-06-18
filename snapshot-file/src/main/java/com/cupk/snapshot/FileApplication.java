package com.cupk.snapshot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by Guo Tianyou on 2023/6/13.
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
@EnableFeignClients("com.cupk.snapshot.service")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
