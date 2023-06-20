package com.cupk.snapshot.mapper;

import com.cupk.snapshot.domain.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author User
* @description 针对表【wfssp_order】的数据库操作Mapper
* @createDate 2023-06-14 12:59:08
* @Entity com.cupk.snapshot.domain/entity.Order
*/
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}




