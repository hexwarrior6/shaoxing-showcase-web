package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalSight;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
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
    public RestBean<String> addLocalSight(@RequestBody LocalSight localSight) {
        if (localSightService.addLocalSight(localSight) > 0) {
            return RestBean.success("添加成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 删除景点
    @DeleteMapping("/{id}")
    public RestBean<String> deleteLocalSight(@PathVariable int id) {
        if (localSightService.deleteLocalSight(id) > 0) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 更新景点
    @PutMapping("/{id}")
    public RestBean<String> updateLocalSight(@PathVariable int id, @RequestBody LocalSight localSight) {
        localSight.setId(id);
        if (localSightService.updateLocalSight(localSight) > 0) {
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 获取指定ID的景点
    @GetMapping("/{id}")
    public RestBean<LocalSight> getLocalSight(@PathVariable int id) {
        return RestBean.success(localSightService.getLocalSightById(id));
    }

    // 获取所有景点
    @GetMapping
    public RestBean<List<LocalSight>> getAllLocalSights() {
        return RestBean.success(localSightService.getAllLocalSights());
    }

    // 搜索美食
    @GetMapping("/search/{name}")
    public RestBean<List<LocalSight>> searchLocalFood(@PathVariable String name) {
        return RestBean.success(localSightService.searchLocalSightsByName(name));
    }
}
