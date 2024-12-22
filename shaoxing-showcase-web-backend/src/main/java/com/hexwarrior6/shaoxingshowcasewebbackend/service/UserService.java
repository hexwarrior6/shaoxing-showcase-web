package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.User;

import java.util.List;

public interface UserService {
    // 添加用户
    int addUser(User user);

    // 删除用户
    int deleteUser(int id);

    // 更新用户
    int updateUser(User user);

    // 根据ID查询用户
    User getUserById(int id);

    // 查询所有用户
    List<User> getAllUsers();
}
