package xyz.flyzz.springsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.flyzz.springsecurity.entity.dao.UserDo;
import xyz.flyzz.springsecurity.mapper.SecurityMapper;

@Service
public class SecurityService extends ServiceImpl<SecurityMapper, UserDo> implements IService<UserDo> {

    public UserDo getByUserName(String userName) {
        QueryWrapper<UserDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return this.getOne(queryWrapper);
    }
}
