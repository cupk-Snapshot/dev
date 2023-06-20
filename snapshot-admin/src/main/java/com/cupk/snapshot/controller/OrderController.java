package com.cupk.snapshot.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.domain.entity.Goods;
import com.cupk.snapshot.domain.entity.Order;
import com.cupk.snapshot.domain.model.vo.AddressVo;
import com.cupk.snapshot.domain.model.vo.GoodsVo;
import com.cupk.snapshot.domain.model.vo.OrderVo;
import com.cupk.snapshot.domain.model.vo.PageVo;
import com.cupk.snapshot.service.AddressService;
import com.cupk.snapshot.service.GoodsService;
import com.cupk.snapshot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询订单信息
     */
    @GetMapping("/all")
    public R getAll(@RequestParam("current") Long current, @RequestParam("size") Long size) {
        Page<Order> page = orderService.page(new Page<>(current, size), null);
        List<Order> orderList = page.getRecords();

//        ArrayList<OrderVo> orderVos = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        orderList.forEach(i -> {
            OrderVo orderVo = BeanUtil.toBean(i, OrderVo.class);
            Map<String, Object> map = BeanUtil.beanToMap(orderVo, new HashMap<>(), false, false);
            map.remove("name");

            //获取订单地址信息
            Address address = addressService.getOne(
                    new LambdaUpdateWrapper<Address>().eq(Address::getAddressId, i.getAddressId()));
            AddressVo addressVo = BeanUtil.toBean(address, AddressVo.class);
//            orderVo.setAddressVo(addressVo);
            map.putAll(BeanUtil.beanToMap(addressVo, new HashMap<>(), false, false));

            //获取商品地址信息
            Goods goods = goodsService.getOne(
                    new LambdaUpdateWrapper<Goods>().eq(Goods::getGoodsId, i.getGoodsId()));
            GoodsVo goodsVo = BeanUtil.toBean(goods, GoodsVo.class);
//            orderVo.setGoodsVo(goodsVo);
            map.putAll(BeanUtil.beanToMap(goodsVo, new HashMap<>(), false, false));

//            orderVos.add(orderVo);
            list.add(map);
        });
        PageVo res = new PageVo(list, page);
        return R.success(res);
    }


//    /**
//     * MySQL条件查询
//     */
//    @GetMapping("/search")
//    public R search(@RequestParam("accept_id") Long acceptId, @RequestParam("status") String status, @RequestParam("is_public") Long isPublic) {
//        //定义查询条件，有可能为null（用户未输入或未选择）
//        QueryWrapper<Accept> queryWrapper = new QueryWrapper<>();
//        queryWrapper
//                .eq(acceptId != null, "accept_id", acceptId)
//                .eq(!StringUtils.isEmpty(status), "status", status)
//                .eq(isPublic != null, "is_public", isPublic);
//
//        List<Accept> res = acceptService.list(queryWrapper);
//        return R.success(res);
//    }
//
    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> ids) {
        boolean flag = orderService.removeBatchByIds(ids);
        if (flag) {
            return R.success("删除成功");
        }else {
            return R.error("删除失败");
        }
    }


}
