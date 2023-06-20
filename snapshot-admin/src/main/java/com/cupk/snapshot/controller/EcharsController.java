package com.cupk.snapshot.controller;

import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.mapper.AcceptMapper;
import com.cupk.snapshot.mapper.PointsMapper;
import com.cupk.snapshot.mapper.ReportMapper;
import com.cupk.snapshot.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charts")
public class EcharsController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private AcceptMapper acceptMapper;

    @Autowired
    private PointsMapper pointsMapper;

    /**
     * 获取系统总用户数
     */
    @GetMapping("/total/user")
    public R totalUser() {
        Integer total = sysUserMapper.getTotalUser();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        return R.success(result);
    }


    /**
     * 获取系统总举报数
     */
    @GetMapping("/total/report")
    public R totalReport() {
        Integer total = reportMapper.getTotalReport();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        return R.success(result);
    }

    /**
     * 获取未处理举报数
     */
    @GetMapping("/total/accept")
    public R totalAccept() {
        Integer total = acceptMapper.getTotalAccept();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        return R.success(result);
    }

    /**
     * 获取总赠送积分
     */
    @GetMapping("/total/points")
    public R totalPoints() {
        Integer total = pointsMapper.getAllPoints();
        Map<String, Integer> result = new HashMap<>();
        result.put("total", total);
        return R.success(result);
    }

    /**
     * 获取违法类别占比
     */
    @GetMapping("/type/percent")
    public R typePercent() {
        List<Double>  list = new ArrayList<>();
        Integer qt = reportMapper.getQt();
        Integer yxbd = reportMapper.getYxbd();
        Integer chd = reportMapper.getChd();
        Integer wztc = reportMapper.getWztc();
        Integer kcddh = reportMapper.getKcddh();
        Integer nx = reportMapper.getNx();
        Double sum = (double) (qt + yxbd + chd + wztc + kcddh + nx);
        list.add(qt*100.00 / sum);
        list.add(yxbd*100.00 / sum);
        list.add(chd*100.00 / sum);
        list.add(wztc*100.00 / sum);
        list.add(kcddh*100.00 / sum);
        list.add(nx*100.00 / sum);
        return R.success(list);
    }


    /**
     * 获取近七日举报数
     */
    @GetMapping("/report/num")
    public R reportNum() {
        List<Integer>  list = new ArrayList<>();
        Integer senven = reportMapper.senven();
        Integer six = reportMapper.six();
        Integer five = reportMapper.five();
        Integer four = reportMapper.four();
        Integer three = reportMapper.three();
        Integer two = reportMapper.two();
        Integer one = reportMapper.one();
        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);
        list.add(five);
        list.add(six);
        list.add(senven);
        return R.success(list);
    }



}
