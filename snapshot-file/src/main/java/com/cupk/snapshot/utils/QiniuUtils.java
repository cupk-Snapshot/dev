package com.cupk.snapshot.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 七牛云工具类
 * Created by Guo Tianyou on 2023/6/16.
 */
@Component
public class QiniuUtils {

    @Value("${qiniu.oss.access-key}")
    private String accessKey;

    @Value("${qiniu.oss.secret-key}")
    private String secretKey;

    @Value("${qiniu.oss.bucket}")
    private String bucket;

    /**
     * upload picture to QiNiu-OSS
     *
     * @param inputStream picture's inputStream
     * @param fileName    the name of picture
     * @return the link of the file
     * @link {https://developer.qiniu.com/kodo/1239/java}
     */
    public Map<String, Object> uploadFile(InputStream inputStream, String fileName, String location) {
        Configuration cfg = new Configuration(Region.huabei());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);

        String key = location + "/" +  UUID.randomUUID().toString().replaceAll("-", "") + fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            /*
             * return the external Links of the picture
             * my domain name: http://hualuosakura.top/
             */
            HashMap<String, Object> result = new HashMap<>();
            result.put("link", "http://rwfye2iaw.hb-bkt.clouddn.com/" + putRet.key);
            result.put("fileName", putRet.key);
            return result;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return new HashMap<>();
    }

    /**
     * delete picture in QiNiu-OSS
     * @param fileName the name of file you want to delete
     * @link {https://developer.qiniu.com/kodo/1239/java#rs-delete}
     */
    public String deleteFile(String fileName) {
        Configuration cfg = new Configuration(Region.xinjiapo());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
            return "delete succeeded";
        } catch (QiniuException ex) {
            // If an exception occurs, it indicates that the deletion failed
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return "delete failed";
        }
    }

}
