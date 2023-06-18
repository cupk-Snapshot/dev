package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Goods;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.domain.model.vo.GoodsVo;
import com.cupk.snapshot.service.GoodsService;
import com.cupk.snapshot.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private OrderController orderController;


    /**
     * 查询全部商品信息
     */
    @GetMapping("/all")
    public R getAll() {
        List<Goods> addresses = goodsService.list();
        ArrayList<GoodsVo> goodsVos = new ArrayList<>();

        addresses.forEach(i -> {
            GoodsVo goodsVo = BeanUtil.toBean(i, GoodsVo.class);
            goodsVos.add(goodsVo);
        });
        return R.success(goodsVos);
    }

    /**
     * 兑换商品
     */
    @GetMapping("/buy")
    public R buy(@RequestParam("user_id") Long userId, @RequestParam("goods_id") Long goodsId,
                 @RequestParam("address_id") Long addressId) {
        Goods goods = goodsService.getOne(new LambdaQueryWrapper<Goods>().eq(Goods::getGoodsId, goodsId));
        if (goods.getStocks() == 0) {
            return R.error("商品售罄");
        }
        Integer totalPoints = pointsService.getTotalPoints(userId);
        if (totalPoints < goods.getPoint()) {
            return R.error("积分不足");
        }
        // 库存数量减1
        LambdaUpdateWrapper<Goods> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Goods::getStocks, goods.getStocks() - 1);
        goodsService.update(updateWrapper);
        // 生成订单记录
        orderController.add(goodsId, userId, addressId);

        return R.success();
    }


}
