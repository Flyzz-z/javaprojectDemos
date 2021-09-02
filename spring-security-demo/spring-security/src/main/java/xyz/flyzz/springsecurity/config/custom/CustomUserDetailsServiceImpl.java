package xyz.flyzz.springsecurity.config.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import xyz.flyzz.springsecurity.entity.dao.UserDo;
import xyz.flyzz.springsecurity.service.SecurityService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Flyzz
 */
@Component("CustomUserDetailsServiceImpl")
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDo userDo = securityService.getByUserName(s);
        log.info(userDo.toString());
        if(ObjectUtils.isEmpty(userDo)) {
            throw new UsernameNotFoundException("user is not exit");
        } else {
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(userDo.getUserName(),userDo.getPassword(),list);
        }
    }

}
