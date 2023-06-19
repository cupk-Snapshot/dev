package com.cupk.snapshot.controller;

import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/all")
    public List<Report> getAll() {
        return reportService.list();
    }

    @PostMapping("/submit")
    public R submit(@RequestParam("user_id") Long userId, @RequestParam("hphm") String hphm, @RequestParam("address") String address,
        @RequestParam("type") String type, @RequestParam("pic1_url") String pic1Url,
                    @RequestParam(value = "pic2_url") String pic2Url, @RequestParam(value = "pic3_url") String pic3Url) {
        String s1 = null, s2 = null, s3 = null;
        if (!"undefined".equals(pic1Url)) {
            s1 = pic1Url;
        }
        if (!"undefined".equals(pic2Url)) {
            s2 = pic2Url;
        }
        if (!"undefined".equals(pic3Url)) {
            s3 = pic3Url;
        }
        Report report = new Report(userId, address, type, hphm, s1, s2, s3);
        reportService.save(report);
        return R.success("提交成功");
    }


}
