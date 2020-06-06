package cn.wqz.study.springboot.session.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class CookieController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("cookie")
    public String cookie(HttpServletRequest request, HttpServletResponse response, String username, String password){

        System.out.println(username + " " + password);
        // getSession方法会触发JSESSION的创建
        HttpSession httpSession = request.getSession();
        String id = httpSession.getId();
        System.out.println("id:" + id);
        if("admin".equals(username) && "admin".equals(password)){
            String result = "欢迎你 " + username;
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for (Cookie cookie : cookies){
                    if("last".equals(cookie.getName())){
                        long time = Long.parseLong(cookie.getValue());
                        Date date = new Date(time);
                        result += "\r\n上一次访问的时间是：" + date.toLocaleString();
                    }
                }
            }
            String time = System.currentTimeMillis() + "";
            Cookie cookie = new Cookie("last", time);
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
            return result;
        }
        return "登陆失败";


    }


}
