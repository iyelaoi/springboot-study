package cn.wqz.study.springboot.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncoderUtil {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pass = bCryptPasswordEncoder.encode("admin");
        System.out.println(pass);
    }

    /**
     * @param source
     * @return
     */
    public static String encrypt(String source){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String result = bCryptPasswordEncoder.encode(source);
        return result;
    }

    public static String getRedisKey(String source){
        return source;
    }
}
