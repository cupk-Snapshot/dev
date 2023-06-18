package com.cupk.snapshot.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.domain.model.vo.AddressVo;
import com.cupk.snapshot.service.AddressService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取全部收货地址
     */
    @GetMapping("/all/{user_id}")
    public R getAll(@PathVariable("user_id") Long userId) {
        List<Address> addresses = addressService.list(
                new LambdaQueryWrapper<Address>().eq(Address::getUserId, userId));
        ArrayList<AddressVo> addressVos = new ArrayList<>();

        addresses.forEach(i -> {
            AddressVo addressVo = BeanUtil.toBean(i, AddressVo.class);
            addressVos.add(addressVo);
        });
        return R.success(addressVos);
    }

    /**
     * 获取默认收货地址
     */
    @GetMapping("/default/{user_id}")
    public R getDefault(@PathVariable("user_id") Long userId) {
        Address address = addressService.getOne(
                new LambdaQueryWrapper<Address>().eq(Address::getIsDefault, 1)
                        .and(qw -> {
                            qw.eq(Address::getUserId, userId);
                        })
        );
        AddressVo addressVo = BeanUtil.toBean(address, AddressVo.class);
        return R.success(addressVo);
    }

    /**
     * 新增收货地址
     */
    @PostMapping("/add")
    public R add(@RequestParam("user_id") Long userId, @RequestParam("name") String name, @RequestParam("phone_num") String phoneNum,
                 @RequestParam("area") String area, @RequestParam("address") String address, @RequestParam("is_default") Integer isDefault) {
        if (isDefault == 1) {
            LambdaUpdateWrapper<Address> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Address::getIsDefault, 0)
                    .eq(Address::getIsDefault, 1)
                    .eq(Address::getUserId, userId);
            addressService.update(updateWrapper);
        }
        Address add = new Address(userId, name, phoneNum, area, address, isDefault);
        addressService.save(add);
        return R.success();
    }

    /**
     * 编辑收货地址
     */
    @PostMapping("/update")
    public R update(@RequestParam("address_id") Long addressId, @RequestParam("user_id") Long userId, @RequestParam("name") String name, @RequestParam("phone_num") String phoneNum,
                    @RequestParam("area") String area, @RequestParam("address") String address, @RequestParam("is_default") Integer isDefault) {
        Address one = addressService.getOne(new LambdaQueryWrapper<Address>().eq(Address::getUserId, userId).eq(Address::getAddressId, addressId));
        Integer oneIsDefault = one.getIsDefault();
        if (oneIsDefault != isDefault) {
            if (isDefault == 1) {
                LambdaUpdateWrapper<Address> update = new LambdaUpdateWrapper<>();
                update.set(Address::getIsDefault, 0)
                        .eq(Address::getIsDefault, 1)
                        .eq(Address::getUserId, userId);
                addressService.update(update);
            }
        }
        LambdaUpdateWrapper<Address> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(Address::getName, name)
                .set(Address::getPhoneNum, phoneNum)
                .set(Address::getArea, area)
                .set(Address::getAddress, address)
                .set(Address::getIsDefault, isDefault)
                .eq(Address::getAddressId, addressId)
                .eq(Address::getUserId, userId);
        addressService.update(updateWrapper);
        return R.success();
    }

    /**
     * 删除收货地址
     */
    @GetMapping("/delete")
    public R update(@RequestParam("user_id") Long userId, @RequestParam("address_id") Long addressId) {
        boolean flag = addressService.remove(
                new LambdaQueryWrapper<Address>().eq(Address::getAddressId, addressId)
                        .and(qw -> {
                            qw.eq(Address::getUserId, userId);
                        })
        );
        if (flag) {
            return R.success();
        } else {
            return R.error("删除失败");
        }
    }

}
