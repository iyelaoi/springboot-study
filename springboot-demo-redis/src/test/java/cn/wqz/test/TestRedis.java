package cn.wqz.test;

import cn.wqz.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        ValueOperations<String, User> operations=redisTemplate.opsForValue();
        operations.set("cn.wqz", user);
        //operations.set("cn.wqz.f", user,1, TimeUnit.SECONDS); // 缓存时间 1 s
        //Thread.sleep(1000);
        redisTemplate.delete("cn.wqz.f");
        boolean exists=redisTemplate.hasKey("cn.wqz.f");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }

        exists=redisTemplate.hasKey("cn.wqz");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }

        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}