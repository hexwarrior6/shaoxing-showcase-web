package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;

import java.util.List;

public interface LocalFoodService {
    // 添加家乡美食
    int addLocalFood(LocalFood localFood);

    // 删除家乡美食
    int deleteLocalFood(int id);

    // 更新家乡美食
    int updateLocalFood(LocalFood localFood);

    // 根据ID查找家乡美食
    LocalFood getLocalFoodById(int id);

    // 获取所有家乡美食
    List<LocalFood> getAllLocalFoods();
}
