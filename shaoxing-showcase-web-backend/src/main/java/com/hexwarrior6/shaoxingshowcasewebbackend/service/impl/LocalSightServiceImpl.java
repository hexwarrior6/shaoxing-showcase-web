package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;


import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalSight;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.LocalSightMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalSightService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalSightServiceImpl implements LocalSightService {

    @Resource
    LocalSightMapper localSightMapper;

    // 添加景点记录
    @Override
    public int addLocalSight(LocalSight localSight) {
        return localSightMapper.insert(localSight);
    }

    // 删除景点记录
    @Override
    public int deleteLocalSight(int id) {
        return localSightMapper.deleteById(id);
    }

    // 更新景点记录
    @Override
    public int updateLocalSight(LocalSight localSight) {
        return localSightMapper.update(localSight);
    }

    // 根据ID查询景点记录
    @Override
    public LocalSight getLocalSightById(int id) {
        return localSightMapper.findById(id);
    }

    // 查询所有景点记录
    @Override
    public List<LocalSight> getAllLocalSights() {
        return localSightMapper.findAll();
    }
}

