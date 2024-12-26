package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalCulture;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.LocalCultureMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalCultureService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalCultureServiceImpl implements LocalCultureService {
    @Resource
    LocalCultureMapper localCultureMapper;

    // 添加文化活动记录
    @Override
    public int addLocalCulture(LocalCulture localCulture) {
        return localCultureMapper.insert(localCulture);
    }

    // 删除文化活动记录
    @Override
    public int deleteLocalCulture(int id) {
        return localCultureMapper.deleteById(id);
    }

    // 更新文化活动记录
    @Override
    public int updateLocalCulture(LocalCulture localCulture) {
        return localCultureMapper.update(localCulture);
    }

    // 根据ID查询文化活动记录
    @Override
    public LocalCulture getLocalCultureById(int id) {
        return localCultureMapper.findById(id);
    }

    // 查询所有文化活动记录
    @Override
    public List<LocalCulture> getAllLocalCultures() {
        return localCultureMapper.findAll();
    }

        // 查询所有文化活动记录
    @Override
    public List<LocalCulture> searchLocalCulturesByName(String name) {
        return localCultureMapper.searchByName(name);
    }
}
