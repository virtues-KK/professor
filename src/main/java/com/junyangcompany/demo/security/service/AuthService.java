package com.junyangcompany.demo.security.service;

import com.junyangcompany.demo.security.mapping.Role;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.mapping.email;
import com.junyangcompany.demo.security.repository.RoleRepo;
import com.junyangcompany.demo.security.repository.UserRepo;
import com.junyangcompany.demo.security.utils.AESUtil;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Objects;

/**
 * author:pan le
 * Date:2019/4/16
 * Time:8:08
 */
@Slf4j
@Service
@Transactional
public class AuthService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepo userRepository;
    private RoleRepo roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepo userRepository,
            RoleRepo roleRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public ResponseEntity<?> register(User userToAdd) throws Exception {
        if (Objects.nonNull(userToAdd) && userToAdd.getPassword().length() != 6){
            return new ResponseEntity<>(
                    "密码必须是6位",
                    HttpStatus.FORBIDDEN
            );
        }
        if (Objects.nonNull(userRepository.findByUsername(userToAdd.getUsername()))) {
            return new ResponseEntity<>(
                    "昵称重复注册",
                    HttpStatus.FORBIDDEN
            );
        }
        if (Objects.nonNull(userRepository.findByEmail(userToAdd.getEmail()))) {
            return new ResponseEntity<>(
                    "邮箱重复注册",
                    HttpStatus.FORBIDDEN
            );
        }
        if (!userToAdd.getEmail().endsWith("@junyanginfo.com")){
            return new ResponseEntity<>(
                    "请使用公司邮箱注册",
                    HttpStatus.FORBIDDEN
            );
        }
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(passwordEncoder.encode(rawPassword));
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = roleRepository.save(new Role("ROLE_USER"));
        }
        userToAdd.setRoles(Collections.singletonList(userRole));
        String genKeyAES = AESUtil.genKeyAES();
        SecretKey secretKey = AESUtil.loadKeyAES(genKeyAES);
        String contents = AESUtil.byte2Base64(AESUtil.encryptAES(userToAdd.getUsername().getBytes(), secretKey));
        String con = genKeyAES + "2308" + contents;
        URLEncoder.encode(con,"GBK");
        String gbk = URLEncoder.encode(userToAdd.getUsername(), "UTF-8");
        String content = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "   <h1>这是一封激活邮件,激活请点击以下链接</h1>" +
                "       <a href='http://192.168.1.40:8099/auth/emailCheck/" + gbk + "' >点开风趣幽默,帅气逼人的我~钟孝漪</a>" +
                "</body>" +
                "</html>";
        email email = com.junyangcompany.demo.security.mapping.email.builder()
                .userEmail(userToAdd.getEmail())
                .content(content)
                .subject("亲爱的,你好!")
                .build();
        mailService.send(email);
        try {
            userToAdd.setIsValid(false);
            userRepository.save(userToAdd);
            return new ResponseEntity<>(
                    userToAdd,
                    HttpStatus.OK
            );
        } catch (DataIntegrityViolationException e) {
            log.debug(e.getMessage());
            return ResponseEntity.badRequest().body("注册出问题啦!!");
        }
    }
    public ResponseEntity checkEmail(String username){
        if (userRepository.findByUsername(username) != null){
            userRepository.updateUserInformation(username);
            return ResponseEntity.ok("注册成功");
        }
        return ResponseEntity.badRequest().body("注册错了");
    }

    public ResponseEntity<String> login(String username, String password) {
        User byUsername = userRepository.findByUsername(username);
        if (Objects.nonNull(byUsername) && byUsername.getIsValid()) {
            // 六位密码
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            try {
                final Authentication authentication = authenticationManager.authenticate(upToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return ResponseEntity.ok(tokenHead + password + jwtTokenUtil.generateToken(userDetails));
            } catch (BadCredentialsException e) {
                log.debug(e.getMessage());
                return ResponseEntity.badRequest().body("账号或者密码错误");
            }
        }
        return ResponseEntity.badRequest().body("请邮件激活");
    }

    public static void main(String[] args) throws IOException {
        try {
            //=================客户端=================
            //hello, i am infi, good night!加密
            String message = "hello, i am infi, good night!";
            //生成AES秘钥，并Base64编码
            String base64Str = AESUtil.genKeyAES();
            System.out.println("AES秘钥Base64编码:" + base64Str);
            //将Base64编码的字符串，转换成AES秘钥
            SecretKey aesKey = AESUtil.loadKeyAES(base64Str);
            //加密
            byte[] encryptAES = AESUtil.encryptAES(message.getBytes(), aesKey);
            //加密后的内容Base64编码
            String byte2Base64 = AESUtil.byte2Base64(encryptAES);
            System.out.println("加密并Base64编码的结果：" + byte2Base64);


            //##############	网络上传输的内容有Base64编码后的秘钥 和 Base64编码加密后的内容		#################
            //===================服务端================
            //将Base64编码的字符串，转换成AES秘钥
            SecretKey aesKey2 = AESUtil.loadKeyAES(base64Str);
            //加密后的内容Base64解码
            byte[] base642Byte = AESUtil.base642Byte(byte2Base64);
            //解密
            byte[] decryptAES = AESUtil.decryptAES(base642Byte, aesKey2);
            //解密后的明文
            System.out.println("解密后的明文: " + new String(decryptAES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
