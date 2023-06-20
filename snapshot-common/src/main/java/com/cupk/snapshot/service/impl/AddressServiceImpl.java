package com.cupk.snapshot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.service.AddressService;
import com.cupk.snapshot.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author User
* @description 针对表【wfssp_address】的数据库操作Service实现
* @createDate 2023-06-13 14:11:08
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




