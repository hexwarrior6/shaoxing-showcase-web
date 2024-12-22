package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocalSight {
    private int id;                // 主键ID
    private String sightName;      // 景点名称
    private String location;       // 景点所在地
    private String description;    // 景点介绍
    private String imageUrl;       // 图片URL
    private String openingHours;   // 开放时间
}
