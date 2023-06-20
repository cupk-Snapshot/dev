package com.cupk.snapshot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cupk.snapshot.domain.entity.Goods;
import org.springframework.stereotype.Repository;

/**
* @author User
* @description 针对表【wfssp_goods】的数据库操作Mapper
* @createDate 2023-06-13 14:11:08
* @Entity com.cupk.snapshot.domain/entity.Goods
*/
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

}




