package xyz.flyzz.securityjwt.config.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.flyzz.securityjwt.common.utils.JwtUtils;
import xyz.flyzz.securityjwt.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String MY_SIGN = "777 ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        Claims claims = null;
        if(httpServletRequest.getHeader("Authorization")!=null&&httpServletRequest.getHeader("Authorization").startsWith(MY_SIGN)) {
            claims = JwtUtils.parse(httpServletRequest.getHeader("Authorization").substring(MY_SIGN.length()));
        }
        if(!ObjectUtils.isEmpty(claims)) {
            String userName = claims.get("userName",String.class);
            List list = claims.get("authority", List.class);
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            for (Object o : list) {
                authorityList.add(new SimpleGrantedAuthority((String) ((LinkedHashMap) o).get("authority")));
            }
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName,"",authorityList);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
