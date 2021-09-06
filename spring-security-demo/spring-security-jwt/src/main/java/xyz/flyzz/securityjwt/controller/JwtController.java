package xyz.flyzz.securityjwt.controller;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import xyz.flyzz.securityjwt.common.utils.JwtUtils;
import xyz.flyzz.securityjwt.pojo.dao.AuthorityDO;
import xyz.flyzz.securityjwt.pojo.dao.UserDO;
import xyz.flyzz.securityjwt.pojo.dto.UserDTO;
import xyz.flyzz.securityjwt.service.AuthorityService;
import xyz.flyzz.securityjwt.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class JwtController {

    @Autowired
    UserService userService;

    @Autowired
    AuthorityService authorityService;

    @PostMapping("/login")
    public UserDTO login(@RequestAttribute(value = "userName",required = false)String userName) {
        if(userName!=null) {
            UserDO userDO = userService.loadUserByName(userName);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDO,userDTO);
            return userDTO;
        } else {
            return null;
        }
    }

    @GetMapping("/refreshtoken")
    public String refresh(@RequestHeader(value = "Authorization",required = false) String token, HttpServletResponse response) {
        if(token==null) {
            return "请重新登录";
        } else {
            Claims claims = JwtUtils.parse(token);
            if(claims==null) {
                return "请重新登录";
            }
            String userName = claims.get("userName",String.class);
            UserDO userDO = userService.loadUserByName(userName);
            List<AuthorityDO> list = authorityService.getAuthorityListById(userDO.getId());
            List<GrantedAuthority> list1 = new ArrayList<>();
            for (AuthorityDO authorityDO:list) {
                list1.add(new SimpleGrantedAuthority(authorityDO.getName()));
            }
            Map<String,Object> accessMap = new HashMap<>();
            Map<String,Object> refreshMap = new HashMap<>();
            accessMap.put("userName",userName);
            refreshMap.put("userName",userName);
            accessMap.put("authority",list1);
            response.setHeader("accessToken",JwtUtils.generate(accessMap, Duration.ofMinutes(30)));
            response.setHeader("refreshToken",JwtUtils.generate(refreshMap,Duration.ofMinutes(90)));
            return "刷新成功";
        }
    }
}
