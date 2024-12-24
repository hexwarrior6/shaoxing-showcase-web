package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.LocalFoodMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalFoodService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalFoodServiceImpl implements LocalFoodService {

    @Resource
    LocalFoodMapper localFoodMapper;

    // 添加家乡美食
    @Override
    public int addLocalFood(LocalFood localFood) {
        return localFoodMapper.insert(localFood);
    }

    // 删除家乡美食
    @Override
    public int deleteLocalFood(int id) {
        return localFoodMapper.deleteById(id);
    }

    // 更新家乡美食
    @Override
    public int updateLocalFood(LocalFood localFood) {
        return localFoodMapper.update(localFood);
    }

    // 根据ID查找家乡美食
    @Override
    public LocalFood getLocalFoodById(int id) {
        return localFoodMapper.findById(id);
    }

    // 获取所有家乡美食
    @Override
    public List<LocalFood> getAllLocalFoods() {
        return localFoodMapper.findAll();
    }

    @Override
    public List<LocalFood> searchLocalFoodByName(String name) {
        return localFoodMapper.searchByName(name);
    }
}
