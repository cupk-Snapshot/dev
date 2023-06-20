package com.cupk.snapshot.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.AcceptVo;
import com.cupk.snapshot.domain.model.vo.MyReport2Vo;
import com.cupk.snapshot.domain.model.vo.ReportVo;
import com.cupk.snapshot.domain.model.vo.UserVo;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.ReportService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private AcceptService acceptService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询首页信息
     */
    @GetMapping("/index")
    public R getAll() {
        List<Map<String, Object>> result = new ArrayList<>();
        //查询公开举报信息
        List<Accept> accepts = acceptService.list(new LambdaQueryWrapper<Accept>().eq(Accept::getIsPublic, 1));
        //封装当前页数据
        accepts.forEach(i -> {
            //获取举报信息
            Report report = reportService.getOne(new LambdaQueryWrapper<Report>().eq(Report::getReportId, i.getReportId()));
            MyReport2Vo reportVo = BeanUtil.toBean(report, MyReport2Vo.class);

            //获取举报用户信息
            SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, report.getUserId()));
            UserVo userVo = BeanUtil.toBean(sysUser, UserVo.class);

            List<Map<String, String>> list = new ArrayList<>();
            HashMap<String, String> url1 = new HashMap<>();
            if (report.getPic1Url() != null) {
                url1.put("url1", report.getPic1Url());
                list.add(url1);
            }
            HashMap<String, String> url2 = new HashMap<>();
            if (report.getPic2Url() != null) {
                url2.put("url2", report.getPic2Url());
                list.add(url2);
            }
            HashMap<String, String> url3 = new HashMap<>();
            if (report.getPic3Url() != null) {
                url3.put("url3", report.getPic3Url());
                list.add(url3);
            }


            Map<String, Object> map = new HashMap<>();
            Map<String, Object> stringObjectMap1 = BeanUtil.beanToMap(userVo, new HashMap<>(), false, false);
            Map<String, Object> stringObjectMap2 = BeanUtil.beanToMap(reportVo, new HashMap<>(), false, false);
            map.putAll(stringObjectMap1);
            map.putAll(stringObjectMap2);
            map.put("createTime", report.getWfTime());
            map.put("imgList", list);

            result.add(map);

        });
        return R.success(result);
    }


}
