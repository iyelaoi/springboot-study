package cn.wqz.study.springboot.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置
 * 支持session时将注解注释去掉
 * 使用Token将其注释
 *
 */
 @Configuration
 @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 配置请求相关
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("HttpSecurity");
        http.authorizeRequests()
                // 需要的权限为 ROLE_USER，没有显式的表达出来
                .antMatchers("/product/**").hasRole("USER") // 匹配某些请求路径，所需要的角色，配置角色权限
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated().and() // 所有请求认证
                .formLogin().and().httpBasic();
    }


    /**
     * 配置用户角色
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 设置自定义的UserDetailsService，从库中获取标准的用户信息，并进行配置，发挥作用
        System.out.println("AuthenticationManagerBuilder");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        /**
         * 创建某些用户，给这些用户赋予权限
         */
        /*
        auth.inMemoryAuthentication()
                .withUser("admin1")
                .password("admin1")
                .roles("ADMIN","USER")
                .and()
                .withUser("user1")
                .password("user1")
                .roles("USER");*/

    }

    /**
     * 用户密码使用加密机制保护
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
