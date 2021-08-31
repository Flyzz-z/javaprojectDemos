package xyz.flyzz.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
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
    @PreAuthorize("hasRole('admin')")
    public String admin() {
        return "I am admin";
    }
}
