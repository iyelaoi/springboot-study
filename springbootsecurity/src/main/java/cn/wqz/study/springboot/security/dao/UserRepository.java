package cn.wqz.study.springboot.security.dao;

import cn.wqz.study.springboot.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByLogin(String login);

}
