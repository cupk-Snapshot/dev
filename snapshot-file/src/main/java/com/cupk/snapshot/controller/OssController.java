package com.cupk.snapshot.controller;

import com.cupk.snapshot.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.cupk.snapshot.utils.QiniuUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private QiniuUtils qiniuUtils;

    /**
     * 上传文件到七牛云对象存储服务器
     * @link {<a href="https://developer.qiniu.com/kodo/1239/java">...</a>}
     */
    @PostMapping("/upload")
    public R uploadFile(@RequestPart(value = "file") MultipartFile[] file,
                        @RequestParam(value = "location", defaultValue = "picture") String location) {
        try {
//            HashMap<String, Object> res = new HashMap<>();
            List<String> paths = new ArrayList<>();
            for(int i = 0; i < file.length; i++) {
                Map<String, Object> map = qiniuUtils.uploadFile(file[i].getInputStream(), file[i].getOriginalFilename(), location);
//                res.put("pic"+(i+1)+"Url", map.get("link"));
                paths.add((String) map.get("link"));
            }
//            Map<String, Object> map = qiniuUtils.uploadFile(file.getInputStream(), file.getOriginalFilename(), location);
            return R.success(paths);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("图片上传失败");
        }
    }

    /**
     * 删除对象存储服务器中的文件
     * @link {<a href="https://developer.qiniu.com/kodo/1239/java#rs-delete">...</a>}
     */
    @GetMapping("/delete")
    public R deleteFile(@RequestParam("file_name") String fileName) {
        try {
            String res = qiniuUtils.deleteFile(fileName);
            return R.success(res);
        } catch (Exception e) {
            return R.error("文件删除失败");
        }
    }


}
