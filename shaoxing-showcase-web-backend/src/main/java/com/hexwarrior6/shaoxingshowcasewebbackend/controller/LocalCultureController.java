package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalCulture;
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
    public int addLocalCulture(@RequestBody LocalCulture localCulture) {
        return localCultureService.addLocalCulture(localCulture);
    }

    // 删除文化活动
    @DeleteMapping("/{id}")
    public int deleteLocalCulture(@PathVariable int id) {
        return localCultureService.deleteLocalCulture(id);
    }

    // 更新文化活动
    @PutMapping("/{id}")
    public int updateLocalCulture(@PathVariable int id, @RequestBody LocalCulture localCulture) {
        localCulture.setId(id);
        return localCultureService.updateLocalCulture(localCulture);
    }

    // 获取指定ID的文化活动
    @GetMapping("/{id}")
    public LocalCulture getLocalCulture(@PathVariable int id) {
        return localCultureService.getLocalCultureById(id);
    }

    // 获取所有文化活动
    @GetMapping
    public List<LocalCulture> getAllLocalCultures() {
        return localCultureService.getAllLocalCultures();
    }
}