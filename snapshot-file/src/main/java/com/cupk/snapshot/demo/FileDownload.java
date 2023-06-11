package com.cupk.snapshot.demo;

import io.minio.*;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileDownload {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://192.168.174.128:9000")
                            .credentials("root", "123456")
                            .build();
            // Download object given the bucket, object name and output file name
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket("photo")
                            .object("cat.jpeg")
                            .filename("C:\\Users\\User\\Desktop\\cat.jpeg")
                            .build());
//            System.out.println(
//                    "object 'cat.jpeg' stored in bucket 'photo' is successfully downloaded to "
//                            + "'C:\\Users\\User\\Desktop\\cat.jpeg'");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}