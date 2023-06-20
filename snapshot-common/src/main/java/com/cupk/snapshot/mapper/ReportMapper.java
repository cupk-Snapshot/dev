package com.cupk.snapshot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cupk.snapshot.domain.entity.Report;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
* @author User
* @description 针对表【wfssp_report】的数据库操作Mapper
* @createDate 2023-06-13 14:11:09
* @Entity com.cupk.snapshot.domain/entity.Report
*/
@Repository
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 获取系统总举报数
     */
    Integer getTotalReport();


    /**
     * 获取闯红灯违法类别数量
     */
    Integer getChd();

    /**
     * 获取逆行违法类别数量
     */
    Integer getNx();


    /**
     * 获取违章停车违法类别数量
     */
    Integer getWztc();

    /**
     * 获取开车打电话违法类别数量
     */
    Integer getKcddh();

    /**
     * 获取压线变道违法类别数量
     */
    Integer getYxbd();

    /**
     * 获取qita违法类别数量
     */
    Integer getQt();

    Integer senven();
    Integer six();
    Integer five();
    Integer four();
    Integer three();
    Integer two();
    Integer one();

}




