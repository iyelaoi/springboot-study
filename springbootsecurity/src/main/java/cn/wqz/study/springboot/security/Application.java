package cn.wqz.study.springboot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合Spring security
 * 1. session开启情况下的cookie认证机制
 * 2. session关闭情况下的token机制
 *
 * Spring security是使用filter机制完成，引入相关jar，默认会开启相关认证
 * 相关流程参考ProcessOn
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
