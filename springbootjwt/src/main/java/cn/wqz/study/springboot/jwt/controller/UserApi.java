package cn.wqz.study.springboot.jwt.controller;

import cn.wqz.study.springboot.jwt.annotation.UserLoginToken;
import cn.wqz.study.springboot.jwt.entity.User;
import cn.wqz.study.springboot.jwt.service.TokenService;
import cn.wqz.study.springboot.jwt.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserApi {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public User register(@RequestBody User user){
        System.out.println(user);
        return userService.save(user);
    }

    /**
     * 登陆成功后，创建JWT token返回给客户端
     * 客户端带着这个token可以访问需要token的api
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user){
        JSONObject jsonObject = new JSONObject();
        User userForBase= userService.findByUsername(user);
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}