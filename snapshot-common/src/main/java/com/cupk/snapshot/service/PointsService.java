package com.cupk.snapshot.service;

import com.cupk.snapshot.domain.entity.Points;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author User
* @description 针对表【wfssp_points】的数据库操作Service
* @createDate 2023-06-14 21:00:07
*/
public interface PointsService extends IService<Points> {

    /**
     * 获取用户总积分
     */
    Integer getTotalPoints(Long userId);

}
