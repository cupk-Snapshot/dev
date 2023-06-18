package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.domain.model.vo.PointsVo;
import com.cupk.snapshot.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    /**
     * 查询积分表明细
     */
    @GetMapping("/all")
    public List<Points> getAll() {
        return pointsService.list();
    }

    /**
     * 查询某用户积分明细
     */
    @GetMapping("/{user_id}")
    public R getUserPoints(@PathVariable("user_id") Long userId) {
        List<Points> points = pointsService.list(
                new LambdaQueryWrapper<Points>().eq(Points::getUserId, userId)
        );
        ArrayList<PointsVo> pointsVos = new ArrayList<>();
        points.forEach(i -> {
            PointsVo pointsVo = BeanUtil.toBean(i, PointsVo.class);
            pointsVos.add(pointsVo);
        });
        return R.success(pointsVos);
    }


}
