package cn.wqz.study.springboot.security.service;

import cn.wqz.study.springboot.security.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUser(Long id);
    List<User> getAllUser();
}
