package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.domain.entity.Goods;
import com.cupk.snapshot.domain.entity.Order;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.AddressVo;
import com.cupk.snapshot.domain.model.vo.OrderVo;
import com.cupk.snapshot.service.AddressService;
import com.cupk.snapshot.service.GoodsService;
import com.cupk.snapshot.service.OrderService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品兑换成功，添加订单记录
     */
    @PostMapping("/add")
    public R add(@RequestParam("goods_id") Long goodsId, @RequestParam("user_id") Long userId,
                 @RequestParam("address_id") Long addressId) {
        SysUser sysUser = sysUserService.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserId, userId)
        );

        String now = DateUtil.now();
        String str = now.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "") + userId;
        Long orderId = Long.valueOf(str);

        Order order = new Order(orderId, goodsId, userId, sysUser.getNickName(), sysUser.getPhoneNum(), addressId);
        orderService.save(order);
        return R.success();
    }


    /**
     * 查询订单信息
     */
    @GetMapping("/all")
    public R all(@RequestParam("user_id") Long userId) {
        List<Order> orders = orderService.list(new LambdaQueryWrapper<Order>().eq(Order::getUserId, userId));
        List<Map<String, Object>> result = new ArrayList<>();
        orders.forEach(i -> {
            Address address = addressService.getOne(new LambdaQueryWrapper<Address>().eq(Address::getAddressId, i.getAddressId()));
            AddressVo addressVo = BeanUtil.toBean(address, AddressVo.class);

            OrderVo orderVo = BeanUtil.toBean(i, OrderVo.class);
            orderVo.setAddressVo(addressVo);

            Goods goods = goodsService.getOne(new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsId, i.getGoodsId()));
            orderVo.setPicUrl(goods.getPicUrl());
            orderVo.setTitle(goods.getTitle());
            orderVo.setPoint(goods.getPoint());

            result.add(BeanUtil.beanToMap(orderVo, new HashMap<>(), false, false));
        });
        return R.success(result);
    }

}
