package com.junyangcompany.demo.controller;

import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.service.AuthService;
import com.junyangcompany.demo.security.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URLDecoder;

/**
 * author:pan le
 * Date:2019/4/15
 * Time:14:36
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class LoginController {

    private final AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public void login() {
        log.info("111111111111");
    }

    @PostMapping("register")
    private ResponseEntity register(@Valid  @RequestBody User user) throws Exception {
        return authService.register(user);
    }

    @GetMapping("/emailCheck/{string}")
    public ResponseEntity EmailCheck(@PathVariable String string) throws Exception {
        String username = URLDecoder.decode(string, "UTF-8");
        return authService.checkEmail(username);
    }

    //找回密码
    @GetMapping("/findBackPassword")
    public void retrievePassword(String email) throws MessagingException {
        authService.retrievePassWord(email);
    }


    @PostMapping("login")
    private ResponseEntity login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
