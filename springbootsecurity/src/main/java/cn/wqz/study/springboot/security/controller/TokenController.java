package cn.wqz.study.springboot.security.controller;

import cn.wqz.study.springboot.security.util.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TokenController {

    private static final String LOGIN = "/getToken";

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping(LOGIN)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            @RequestParam String username,
            @RequestParam String password) throws AuthenticationException {
        System.out.println("password:" + password);
        return ResponseEntity.ok(generateToken(username, password));
    }

    /**
     *
     * 获取token
     *
     * 这只是个模拟
     * 实际漏洞百出
     * @param username .
     * @param password .
     * @return
     */
    private String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 持久化的redis
        String token = EncoderUtil.getRedisKey(userDetails.getUsername());
        // 实际上此处只存储username意义不大，常规需要存储用户信息（包括password及权限信息）
        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        String stringValue = redisTemplate.opsForValue().get(token);
        System.out.println(stringValue);
        System.out.println(redisTemplate.hasKey(token));
        return token;
    }
}
