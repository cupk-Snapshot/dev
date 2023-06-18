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
        @RequestParam("type") String type, @RequestParam("pic1_url") String pic1Url, @RequestParam("pic2_url") String pic2Url, @RequestParam("pic3_url") String pic3Url) {
        Report report = new Report(userId, address, type, hphm, pic1Url, pic2Url, pic3Url);
        reportService.save(report);
        return R.success("提交成功");
    }


}
