package xyz.flyzz.securityjwt.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.flyzz.securityjwt.common.utils.JwtUtils;
import xyz.flyzz.securityjwt.pojo.dto.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Flyzz
 */

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/login", "POST");

    private AuthenticationManager authenticationManager;

    protected CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        super.setAuthenticationManager(authenticationManager );
    }


    static class UserInfo{
        public String userName;
        public String password;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        if(!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            ObjectMapper objectMapper = new ObjectMapper();
            UserInfo userInfo = null;
            try {
                userInfo = objectMapper.readValue(request.getInputStream(),UserInfo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert userInfo != null;
            if(userInfo.userName==null) {
                throw new AuthenticationServiceException("用户名不能为空");
            } else {
                userInfo.userName = userInfo.userName.trim();
                userInfo.password = userInfo.password.trim();
                return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userInfo.userName,userInfo.password));
            }
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Map<String,Object> accessMap = new HashMap<>();
        Map<String,Object> refreshMap = new HashMap<>();
        User user = (User) authResult.getPrincipal();
        accessMap.put("userName",user.getUsername());
        refreshMap.put("userName",user.getUsername());
        accessMap.put("authority",authResult.getAuthorities());

        request.setAttribute("userName", user.getUsername());
        response.setHeader("accessToken",JwtUtils.generate(accessMap,Duration.ofMinutes(3)));
        response.setHeader("refreshToken",JwtUtils.generate(refreshMap,Duration.ofMinutes(9)));
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request,response);
    }

}
