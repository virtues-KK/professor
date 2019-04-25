package com.junyangcompany.demo.security.config;


import com.junyangcompany.demo.security.mapping.Role;
import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/4/15
 * Time:14:43
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            userRepo.findByUsername(username);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有该用户");
        }
        return UserFactory.create(user);
    }
}

final class UserFactory {

    static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        );
    }

    //将与用户类一对多的角色类的名称集合转换为 GrantedAuthority 集合
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
