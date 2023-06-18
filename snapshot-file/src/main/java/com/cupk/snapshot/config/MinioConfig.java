//package com.cupk.snapshot.config;
//
//import io.minio.MinioClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Minio配置类
// * Created by Guo Tianyou on 2023/6/16.
// */
//@Configuration
//public class MinioConfig {
//
//    @Value("${minio.endpoint}")
//    private String endPoint;
//
//    @Value("${minio.access-key}")
//    private String accessKey;
//
//    @Value("${minio.secret-key}")
//    private String secretKey;
//
//    @Value("${minio.bucket.files}")
//    private String bucketFiles;
//
//
//    @Bean
//    public MinioClient minioClient() {
//        return MinioClient.builder()
//                .endpoint(endPoint)
//                .credentials(accessKey, secretKey)
//                .build();
//    }
//
//
//}
