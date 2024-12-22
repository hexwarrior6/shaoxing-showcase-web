package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalSight;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalSightService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local-sights")
public class LocalSightController {

    @Resource
    LocalSightService localSightService;

    // 添加景点
    @PostMapping
    public int addLocalSight(@RequestBody LocalSight localSight) {
        return localSightService.addLocalSight(localSight);
    }

    // 删除景点
    @DeleteMapping("/{id}")
    public int deleteLocalSight(@PathVariable int id) {
        return localSightService.deleteLocalSight(id);
    }

    // 更新景点
    @PutMapping("/{id}")
    public int updateLocalSight(@PathVariable int id, @RequestBody LocalSight localSight) {
        localSight.setId(id);
        return localSightService.updateLocalSight(localSight);
    }

    // 获取指定ID的景点
    @GetMapping("/{id}")
    public LocalSight getLocalSight(@PathVariable int id) {
        return localSightService.getLocalSightById(id);
    }

    // 获取所有景点
    @GetMapping
    public List<LocalSight> getAllLocalSights() {
        return localSightService.getAllLocalSights();
    }
}
