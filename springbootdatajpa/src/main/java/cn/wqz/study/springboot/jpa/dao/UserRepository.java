package cn.wqz.study.springboot.jpa.dao;

import cn.wqz.study.springboot.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(long l);
    List<User> findAllByName(String name);
}
