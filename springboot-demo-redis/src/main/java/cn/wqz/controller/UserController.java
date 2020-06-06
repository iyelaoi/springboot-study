package cn.wqz.controller;

import cn.wqz.dao.UserRepository;
import cn.wqz.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser() {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    /**
     * 使用redis进行spring session 共享
     * 在config中配置redis session缓存
     * @param session
     * @return
     */
    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    @RequestMapping("/get/{userName}")
    @ResponseBody
    public User get(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @RequestMapping("/insert")
    public User insert(){
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "123");
        userRepository.save(user);
        return user;
    }
}
