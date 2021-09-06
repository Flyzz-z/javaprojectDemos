package xyz.flyzz.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SecurityController {

    @GetMapping("/hello")
    public String hello() {
        return "hello"+SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @PreAuthorize("hasAuthority('print')")
    @GetMapping("/print")
    public String print() {
        return "permit print";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole(\"ADMIN\")")
    public String admin() {
        return "I am admin";
    }
}
