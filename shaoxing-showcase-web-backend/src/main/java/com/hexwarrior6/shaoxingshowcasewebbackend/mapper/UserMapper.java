package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 添加用户
    @Insert("INSERT INTO user (username, real_name, hobby, avatar, address) VALUES (#{username}, #{realName}, #{hobby}, #{avatar}, #{address})")
    int insert(User user);

    // 删除用户
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(int id);

    // 更新用户
    @Update("UPDATE user SET username = #{username}, real_name = #{realName}, hobby = #{hobby}, avatar = #{avatar}, address = #{address} WHERE id = #{id}")
    int update(User user);

    // 根据ID查询用户
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(int id);

    // 查询所有用户
    @Select("SELECT * FROM user")
    List<User> findAll();
}
