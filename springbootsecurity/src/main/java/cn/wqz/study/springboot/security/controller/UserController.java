package cn.wqz.study.springboot.security.controller;

import cn.wqz.study.springboot.security.model.User;
import cn.wqz.study.springboot.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/test")
    String test(){
        log.info("test controller -> thymeleaf");
        return "index";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    String get(){
        log.info("userController get");
        return "user get";
    }

    @ResponseBody
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    String hello(String name){
        System.out.println(name);
        return "hello world";
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    User add(@RequestParam("id") String id, @RequestParam("login") String login,
             @RequestParam("password") String password, @RequestParam("role") String role){
        User user = new User();
        user.setId(Long.valueOf(id));
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        log.info("insert a user {} to table", user);
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    List<User> getAll(){
        List<User> list = userService.getAllUser();
        log.info("find all user:{}", list.size());
        return list;
    }
}
