package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.model.vo.MyReportVo;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.ReportService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private AcceptService acceptService;


    /**
     * 我的举报信息
     */
    @GetMapping("/all")
    public R getAll(@RequestParam("user_id") Long userId) {
        List<Report> reports = reportService.list(
                new LambdaQueryWrapper<Report>().eq(Report::getUserId, userId));
        List<MyReportVo> res = new ArrayList<>();

//        List<Accept> list = acceptService.list();

        for (Report report : reports) {
            Accept accept = acceptService.getOne(
                    new LambdaQueryWrapper<Accept>().eq(Accept::getReportId, report.getReportId()));

            MyReportVo myReportVo = BeanUtil.toBean(report, MyReportVo.class);
            myReportVo.setStatus(accept.getStatus());

            res.add(myReportVo);
        }


        return R.success(res);
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

        Accept accept = new Accept(report.getReportId(),1,"等待审核");


        acceptService.save(accept);
        return R.success("提交成功");
    }


}
