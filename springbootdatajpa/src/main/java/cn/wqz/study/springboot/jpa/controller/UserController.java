package cn.wqz.study.springboot.jpa.controller;

import cn.wqz.study.springboot.jpa.dao.UserRepository;
import cn.wqz.study.springboot.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/select")
    @ResponseBody
    List<User> select(){
        return userRepository.findAllByName("wqz");
    }

    @RequestMapping("/insert")
    @ResponseBody
    User insert(){
        User user = new User();
        user.setName("wqz");
        userRepository.save(user);
        return user;
    }
}
