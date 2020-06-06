package cn.wqz.service;

import cn.wqz.model.User;

public interface UserService {

    User select(String name);

    User insert(User user);

    User delete(User user);

    User update(User user);
}
