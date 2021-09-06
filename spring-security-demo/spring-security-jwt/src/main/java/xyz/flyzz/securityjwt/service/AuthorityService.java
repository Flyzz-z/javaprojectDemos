package xyz.flyzz.securityjwt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.flyzz.securityjwt.pojo.dao.AuthorityDO;

import java.math.BigInteger;
import java.util.List;

public interface AuthorityService extends IService<AuthorityDO> {

    List<AuthorityDO> getAuthorityListById(BigInteger id);

}
