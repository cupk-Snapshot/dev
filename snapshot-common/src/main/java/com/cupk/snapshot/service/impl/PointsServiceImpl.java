package com.cupk.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.service.PointsService;
import com.cupk.snapshot.mapper.PointsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【wfssp_points】的数据库操作Service实现
* @createDate 2023-06-14 21:00:07
*/
@Service
public class PointsServiceImpl extends ServiceImpl<PointsMapper, Points>
    implements PointsService{

    @Autowired
    private PointsMapper pointsMapper;

    /**
     * 获取用户总积分
     */
    @Override
    public Integer getTotalPoints(Long userId) {
        return pointsMapper.getTotalPoints(userId);
    }
}




