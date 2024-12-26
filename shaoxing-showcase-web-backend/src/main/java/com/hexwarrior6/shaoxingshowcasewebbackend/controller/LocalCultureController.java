package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalCulture;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalCultureService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local-cultures")
public class LocalCultureController {

    @Resource
    private LocalCultureService localCultureService;

    // 添加文化活动
    @PostMapping
    public RestBean<String> addLocalCulture(@RequestBody LocalCulture localCulture) {
        if (localCultureService.addLocalCulture(localCulture) > 0) {
            return RestBean.success("添加成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 删除文化活动
    @DeleteMapping("/{id}")
    public RestBean<String> deleteLocalCulture(@PathVariable int id) {
        if (localCultureService.deleteLocalCulture(id) > 0) {
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 更新文化活动
    @PutMapping("/{id}")
    public RestBean<String> updateLocalCulture(@PathVariable int id, @RequestBody LocalCulture localCulture) {
        localCulture.setId(id);
        if (localCultureService.updateLocalCulture(localCulture) > 0) {
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 获取指定ID的文化活动
    @GetMapping("/{id}")
    public RestBean<LocalCulture> getLocalCulture(@PathVariable int id) {
        return RestBean.success(localCultureService.getLocalCultureById(id));
    }

    // 获取所有文化活动
    @GetMapping
    public RestBean<List<LocalCulture>> getAllLocalCultures() {
        return RestBean.success(localCultureService.getAllLocalCultures());
    }

    // 搜索文化活动
    @GetMapping("/search/{name}")
    public RestBean<List<LocalCulture>> searchLocalCulture(@PathVariable String name) {
        return RestBean.success(localCultureService.searchLocalCulturesByName(name));
    }
}