package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocalFood {
    private int id;                // 主键ID
    private String foodName;       // 美食名称
    private String description;    // 美食描述
    private String ingredients;    // 主要原料
    private String imageUrl;       // 图片URL
    private String origin;         // 美食起源地
}
