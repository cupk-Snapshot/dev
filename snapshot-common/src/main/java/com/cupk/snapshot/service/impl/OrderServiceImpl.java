package com.cupk.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupk.snapshot.domain.entity.Order;
import com.cupk.snapshot.service.OrderService;
import com.cupk.snapshot.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【wfssp_order】的数据库操作Service实现
* @createDate 2023-06-14 12:59:08
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




