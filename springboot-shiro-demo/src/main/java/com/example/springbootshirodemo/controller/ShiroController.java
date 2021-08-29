package com.example.springbootshirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ShiroController {

    @GetMapping("/login/{userName}/{password}")
    public String auth(@PathVariable String userName, @PathVariable String password) {
        Subject subject = SecurityUtils.getSubject();
        String result = "";
        try {
            // 打印是否授权
            System.out.println("是否授权：" + subject.isAuthenticated());
            // 用户登陆认证
            subject.login(new UsernamePasswordToken(userName, password));
            // true 登陆之后就是授权
            System.out.println("是否授权：" + subject.isAuthenticated());
            return "认证成功";
        } catch (UnknownAccountException e) {
            System.out.println("用户不存在");
            result = "用户不存在";
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            result = "密码错误";
        } catch (AuthenticationException e) {
            System.out.println("认证失败");
            result = "认证失败";
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/get")
    @RequiresPermissions("shiro:get")
    public String get() {
        Subject subject = SecurityUtils.getSubject();
        return "get it！" + subject.getPrincipal();
    }

    @GetMapping("/post")
    @RequiresPermissions("shiro:post")
    public String post() {
        Subject subject = SecurityUtils.getSubject();
        return "post it！" + subject.getPrincipal() + subject.isAuthenticated();
    }

    @GetMapping("/delete")
    @RequiresRoles("admin")
    public String delete() {
        Subject subject = SecurityUtils.getSubject();
        return "delete it！" + subject.getPrincipal();
    }
}
