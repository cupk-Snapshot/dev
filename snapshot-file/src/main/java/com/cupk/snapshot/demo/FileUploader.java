package com.cupk.snapshot.demo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://192.168.174.128:9000")
                            .credentials("root", "123456")
                            .build();
            // Make 'photo' bucket if not exist.
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("photo").build());
            if (!found) {
                // Make a new bucket called 'photo'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("photo").build());
            } else {
                System.out.println("Bucket 'photo' already exists.");
            }
            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket 'photo'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("photo")
                            .object("cat.jpeg")
                            .filename("C:\\Users\\User\\Pictures\\cartoon\\cat.jpeg")
                            .build());
            System.out.println(
                    "'C:\\Users\\User\\Pictures\\cartoon\\cat.jpeg' is successfully uploaded as "
                            + "object 'cat.jpeg' to bucket 'photo'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}