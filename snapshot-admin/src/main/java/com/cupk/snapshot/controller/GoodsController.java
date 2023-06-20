package com.cupk.snapshot.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Goods;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.domain.model.vo.AcceptVo;
import com.cupk.snapshot.domain.model.vo.PageVo;
import com.cupk.snapshot.domain.model.vo.ReportVo;
import com.cupk.snapshot.domain.model.vo.UserVo;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.GoodsService;
import com.cupk.snapshot.service.ReportService;
import com.cupk.snapshot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询受理表信息
     */
    @GetMapping("/all")
    public R getAll(@RequestParam("current") Long current, @RequestParam("size") Long size) {
        Page<Goods> page = goodsService.page(new Page<>(current, size), null);
        List<Goods> goodsArrayList = page.getRecords();
        PageVo res = new PageVo(goodsArrayList, page);
        return R.success(res);
    }

    /**
     * 更新商品
     */
    @PostMapping("/update")
    public R getAll(@RequestParam("goodsId") Long goodsId, @RequestParam("picUrl") String picUrl, @RequestParam("point") Long point,
                    @RequestParam("stocks") Long stocks, @RequestParam("title") String title) {
        LambdaUpdateWrapper<Goods> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(stocks != null, Goods::getStocks, stocks)
                .set(!StringUtils.isEmpty(picUrl), Goods::getPicUrl, picUrl)
                .set(point != null, Goods::getPoint, point)
                .set(point != null, Goods::getPoint, point)
                .eq(Goods::getGoodsId, goodsId);
        boolean flag = goodsService.update(updateWrapper);
        if (flag) {
            return R.success("修改成功");
        } else {
            return R.error("修改失败");
        }
    }

    /**
     * 新增商品
     */
    @PostMapping("/add")
    public R getAll(@RequestParam("picUrl") String picUrl, @RequestParam("point") Integer point,
                    @RequestParam("stocks") Integer stocks, @RequestParam("title") String title) {
        Goods goods = new Goods(title, picUrl, point, stocks);
        boolean flag = goodsService.save(goods);
        if (flag) {
            return R.success("添加成功");
        } else {
            return R.error("添加失败");
        }
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
        boolean flag = goodsService.removeBatchByIds(ids);
        if (flag) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }


}
