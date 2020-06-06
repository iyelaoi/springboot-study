package cn.wqz.study.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 此Controller只希望被Admin用户访问
 */
@Controller
@RequestMapping("/admin")
public class AdminTestController {

    @RequestMapping("/home")
    @ResponseBody
    public String productInfo(){
        return " admin home page ";
    }
}