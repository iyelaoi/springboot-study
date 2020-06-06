package cn.wqz.study.springboot.security.service.impl;

import cn.wqz.study.springboot.security.dao.UserRepository;
import cn.wqz.study.springboot.security.model.User;
import cn.wqz.study.springboot.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return  userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
