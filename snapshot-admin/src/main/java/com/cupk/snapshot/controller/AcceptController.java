package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.AcceptVo;
import com.cupk.snapshot.domain.model.vo.PageVo;
import com.cupk.snapshot.domain.model.vo.ReportVo;
import com.cupk.snapshot.domain.model.vo.UserVo;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.PointsService;
import com.cupk.snapshot.service.ReportService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
@RestController
@RequestMapping("/accept")
public class AcceptController {

    @Autowired
    private AcceptService acceptService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PointsService pointsService;

    /**
     * 分页查询受理表信息
     */
    @GetMapping("/all")
    public R getAll(@RequestParam("current") Long current, @RequestParam("size") Long size) {
        Page<Accept> page = acceptService.page(new Page<>(current, size), null);
        List<Accept> accepts = page.getRecords();
//        ArrayList<AcceptVo> acceptVos = new ArrayList<>();

        List<Map<String, Object>> result = new ArrayList<>();

        //封装当前页数据
        accepts.forEach(i -> {
            Map<String, Object> map = new HashMap<>();
            AcceptVo acceptVo = BeanUtil.toBean(i, AcceptVo.class);
            //获取举报信息
            Report report = reportService.getOne(
                    new LambdaQueryWrapper<Report>().eq(Report::getReportId, i.getReportId()));
            ReportVo reportVo = BeanUtil.toBean(report, ReportVo.class);

            //获取举报用户信息
            SysUser sysUser = sysUserService.getOne(
                    new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, report.getUserId()));
            UserVo userVo = BeanUtil.toBean(sysUser, UserVo.class);

            map.putAll(BeanUtil.beanToMap(acceptVo, new HashMap<>(), false, true));
            map.putAll(BeanUtil.beanToMap(reportVo, new HashMap<>(), false, true));
            map.putAll(BeanUtil.beanToMap(userVo, new HashMap<>(), false, true));

            result.add(map);
        });
        PageVo res = new PageVo(result, page);
        return R.success(res);
    }


    @PostMapping("/examine")
    public R examine(@RequestParam("userId") Long userId, @RequestParam("acceptId") Long acceptId,
                     @RequestParam("status") String status, @RequestParam("point") Integer point,
                        @RequestParam("examineId") Long examineId, @RequestParam("examineName") String examineName) {
        LambdaUpdateWrapper<Accept> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Accept::getStatus, status)
                .set(Accept::getExamineId, examineId)
                .set(Accept::getExamineName, examineName)
                .eq(Accept::getAcceptId, acceptId);
        acceptService.update(updateWrapper);
        Integer balance = pointsService.getTotalPoints(userId);
        Points points = new Points(userId, "举报通过，奖励积分", point, balance + point);
        boolean flag = pointsService.save(points);
        if (flag) {
            return R.success("修改（审核）成功");
        }else {
            return R.success("修改（审核）失败");
        }

    }

    /**
     * MySQL条件查询
     */
    @GetMapping("/search")
    public R search(@RequestParam("accept_id") Long acceptId, @RequestParam("status") String status, @RequestParam("is_public") Long isPublic) {
        //定义查询条件，有可能为null（用户未输入或未选择）
        QueryWrapper<Accept> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(acceptId != null, "accept_id", acceptId)
                .eq(!StringUtils.isEmpty(status), "status", status)
                .eq(isPublic != null, "is_public", isPublic);

        List<Accept> res = acceptService.list(queryWrapper);
        return R.success(res);
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        boolean flag = acceptService.removeBatchByIds(ids);
        if (flag) {
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }
    }


}
