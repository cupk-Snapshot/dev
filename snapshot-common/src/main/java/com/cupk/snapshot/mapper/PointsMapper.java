package com.cupk.snapshot.mapper;

import com.cupk.snapshot.domain.entity.Points;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author User
* @description 针对表【wfssp_points】的数据库操作Mapper
* @createDate 2023-06-14 21:00:06
* @Entity com.cupk.snapshot.domain/entity.Points
*/
@Repository
public interface PointsMapper extends BaseMapper<Points> {

    /**
     * 获取用户总积分
     */
    Integer getTotalPoints(@Param("userId") Long userId);

}




