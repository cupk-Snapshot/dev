package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Order;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.AddressService;
import com.cupk.snapshot.service.OrderService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/all")
    public R getAll() {
        List<Order> orders = orderService.list();
        return R.success(orders);
    }

    /**
     * 商品兑换成功，添加订单记录
     */
    @PostMapping("/add")
    public R add(@RequestParam("goods_id") Long goodsId, @RequestParam("user_id") Long userId,
                 @RequestParam("address_id") Long addressId) {
        SysUser sysUser = sysUserService.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId)
        );
        Order order = new Order(goodsId, userId, sysUser.getName(), sysUser.getPhoneNum(), addressId);
        orderService.save(order);
        return R.success();
    }

}
