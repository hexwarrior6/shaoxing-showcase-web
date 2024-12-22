package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;              // 用户ID
    private String username;     // 用户名
    private String realName;     // 真实姓名
    private String hobby;        // 爱好
    private String avatar;       // 头像图片路径或URL
    private String address;      // 地址
}
