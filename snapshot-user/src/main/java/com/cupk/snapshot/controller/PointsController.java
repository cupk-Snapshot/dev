package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.domain.model.vo.PointsVo;
import com.cupk.snapshot.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/details")
    public R getUserPoints(@RequestParam("user_id") Long userId) {
        Map<String, Object> result = new HashMap<>();
        //用户总积分
        int total = 0;
        List<Points> points = pointsService.list(
                new LambdaQueryWrapper<Points>().eq(Points::getUserId, userId)
        );
        ArrayList<PointsVo> pointsVos = new ArrayList<>();

        for (Points point : points) {
            Integer exchange = point.getExchange();
            total += exchange;
            PointsVo pointsVo = BeanUtil.toBean(point, PointsVo.class);
            pointsVos.add(pointsVo);
        }

//        points.forEach(i -> {
//            Integer exchange = i.getExchange();
//            totxal += exchange;
//            PointsVo pointsVo = BeanUtil.toBean(i, PointsVo.class);
//            pointsVos.add(pointsVo);
//        });
        result.put("details", pointsVos);
        result.put("total", total);


        return R.success(result);
    }


}
