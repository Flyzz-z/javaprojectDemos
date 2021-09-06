package xyz.flyzz.securityjwt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.flyzz.securityjwt.pojo.dao.UserDO;

public interface UserService extends IService<UserDO> {

    UserDO loadUserByName(String userName);
}
