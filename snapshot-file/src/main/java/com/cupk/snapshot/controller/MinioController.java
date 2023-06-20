//package com.cupk.snapshot.controller;
//
//import com.cupk.snapshot.domain.R;
//import io.minio.BucketExistsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.UploadObjectArgs;
//import io.minio.errors.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
///**
// * Created by Guo Tianyou on 2023/6/16.
// */
//@RestController
//@RequestMapping("/minio")
//public class MinioController {
//
//    @Autowired
//    private MinioClient minioClient;
//
//    @PostMapping("/upload")
//    public R upload(@RequestPart("file_date")MultipartFile fileDate) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//
//
//
//        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("photo").build());
//        if (!found) {
//            // Make a new bucket called 'photo'.
//            minioClient.makeBucket(MakeBucketArgs.builder().bucket("photo").build());
//        } else {
//            System.out.println("Bucket 'photo' already exists.");
//        }
//        // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket 'photo'.
//        minioClient.uploadObject(
//                UploadObjectArgs.builder()
//                        .bucket("photo")
//                        .object("cat.jpeg")
//                        .filename("C:\\Users\\User\\Pictures\\cartoon\\cat.jpeg")
//                        .build());
//        System.out.println(
//                "'C:\\Users\\User\\Pictures\\cartoon\\cat.jpeg' is successfully uploaded as "
//                        + "object 'cat.jpeg' to bucket 'photo'.");
//
//
//        return R.success();
//    }
//
//}
