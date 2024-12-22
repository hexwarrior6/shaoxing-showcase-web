package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.User;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.UserMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    // 添加用户
    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    // 删除用户
    @Override
    public int deleteUser(int id) {
        return userMapper.deleteById(id);
    }

    // 更新用户
    @Override
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    // 根据ID查询用户
    @Override
    public User getUserById(int id) {
        return userMapper.findById(id);
    }

    // 查询所有用户
    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
}
