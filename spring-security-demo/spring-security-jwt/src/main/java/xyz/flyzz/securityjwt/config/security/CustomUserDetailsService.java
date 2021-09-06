package xyz.flyzz.securityjwt.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.flyzz.securityjwt.pojo.dao.AuthorityDO;
import xyz.flyzz.securityjwt.pojo.dao.UserDO;
import xyz.flyzz.securityjwt.service.AuthorityService;
import xyz.flyzz.securityjwt.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDO userDO = userService.loadUserByName(s);
        if(userDO!=null) {
            List<AuthorityDO> list = authorityService.getAuthorityListById(userDO.getId());
            List<GrantedAuthority> list1 = new ArrayList<>();
            for (AuthorityDO authorityDO:list) {
                list1.add(new SimpleGrantedAuthority(authorityDO.getName()));
            }
            return new User(userDO.getUserName(),userDO.getPassword(),list1);
        } else {
            throw new UsernameNotFoundException("用户名不存在");
        }
    }
}
