package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocalCulture {
    private int id;                // 主键ID
    private String cultureName;    // 文化活动名称
    private String description;    // 文化活动介绍
    private String significance;   // 文化活动的意义
    private String imageUrl;       // 图片URL
    private String time;
}
