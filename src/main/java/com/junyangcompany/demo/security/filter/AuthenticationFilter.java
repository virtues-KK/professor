package com.junyangcompany.demo.security.filter;

import com.junyangcompany.demo.security.mapping.User;
import com.junyangcompany.demo.security.repository.UserRepo;
import com.junyangcompany.demo.security.utils.JwtTokenUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * author:pan le
 * Date:2019/4/16
 * Time:19:12
 */
@Component
@NoArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = "Authorization";
        String header = httpServletRequest.getHeader(tokenHeader);
        if (Objects.nonNull(header) && header.length() >15){
            String token = header.substring(12);
            String passWord = header.substring(6,12);
            String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usernameFromToken,passWord);
            User byUsername = userRepo.findByUsername(usernameFromToken);
            if (Objects.nonNull(byUsername.getUsername())){
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
