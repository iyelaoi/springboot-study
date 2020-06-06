package cn.wqz.study.springboot.jwt.dao;

import cn.wqz.study.springboot.jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersById(String id);
    User getUsersByUsername(String username);
}
