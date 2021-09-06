package xyz.flyzz.securityjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.flyzz.securityjwt.pojo.dao.AuthorityDO;
import xyz.flyzz.securityjwt.mapper.AuthorityMapper;
import xyz.flyzz.securityjwt.service.AuthorityService;

import java.math.BigInteger;
import java.util.List;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, AuthorityDO> implements AuthorityService {
    @Override
    public List<AuthorityDO> getAuthorityListById(BigInteger id) {
        List<BigInteger> idList = this.baseMapper.selectAuthorityIdsByUser(id);
        return this.listByIds(idList);
    }
}
