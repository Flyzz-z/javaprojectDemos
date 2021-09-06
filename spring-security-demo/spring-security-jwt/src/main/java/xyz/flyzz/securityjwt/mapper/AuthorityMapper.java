package xyz.flyzz.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.flyzz.securityjwt.pojo.dao.AuthorityDO;

import java.math.BigInteger;
import java.util.List;

public interface AuthorityMapper extends BaseMapper<AuthorityDO> {

    List<BigInteger> selectAuthorityIdsByUser(BigInteger id);
}
