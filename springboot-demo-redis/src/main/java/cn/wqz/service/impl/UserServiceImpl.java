package cn.wqz.service.impl;

import cn.wqz.dao.UserRepository;
import cn.wqz.model.User;
import cn.wqz.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class UserServiceImpl implements UserService, InitializingBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private ValueOperations valueOperations;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public User select(String name) {
        User user = (User) valueOperations.get(name);
        if(user == null){
            synchronized (this.getClass()){
                if((user = (User)valueOperations.get(name)) == null){
                    user = userRepository.findByUserName(name);
                    valueOperations.set(name, user);
                }
            }
        }
        return user;
    }

    @Override
    public User insert(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }


}
