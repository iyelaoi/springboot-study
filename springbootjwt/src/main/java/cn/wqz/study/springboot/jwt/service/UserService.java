package cn.wqz.study.springboot.jwt.service;


import cn.wqz.study.springboot.jwt.dao.UserRepository;
import cn.wqz.study.springboot.jwt.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User findUserById(String userId) {
        return userRepository.getUsersById(userId);
    }

    public User findByUsername(User user) {
        return userRepository.getUsersByUsername(user.getUsername());
    }
}
