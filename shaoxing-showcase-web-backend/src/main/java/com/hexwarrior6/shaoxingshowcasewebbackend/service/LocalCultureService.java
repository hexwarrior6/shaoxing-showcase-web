package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalCulture;

import java.util.List;

public interface LocalCultureService {
    // 添加文化活动记录
    int addLocalCulture(LocalCulture localCulture);

    // 删除文化活动记录
    int deleteLocalCulture(int id);

    // 更新文化活动记录
    int updateLocalCulture(LocalCulture localCulture);

    // 根据ID查询文化活动记录
    LocalCulture getLocalCultureById(int id);

    // 查询所有文化活动记录
    List<LocalCulture> getAllLocalCultures();

    List<LocalCulture> searchLocalCulturesByName(String name);
}
