package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalSight;

import java.util.List;

public interface LocalSightService {
    // 添加景点记录
    int addLocalSight(LocalSight localSight);

    // 删除景点记录
    int deleteLocalSight(int id);

    // 更新景点记录
    int updateLocalSight(LocalSight localSight);

    // 根据ID查询景点记录
    LocalSight getLocalSightById(int id);

    // 查询所有景点记录
    List<LocalSight> getAllLocalSights();
}
