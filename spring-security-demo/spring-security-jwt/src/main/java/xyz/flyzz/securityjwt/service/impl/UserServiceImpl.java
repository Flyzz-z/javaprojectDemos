package xyz.flyzz.securityjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.flyzz.securityjwt.pojo.dao.UserDO;
import xyz.flyzz.securityjwt.mapper.UserMapper;
import xyz.flyzz.securityjwt.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public UserDO loadUserByName(String userName) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        return this.getOne(queryWrapper);
    }


}
