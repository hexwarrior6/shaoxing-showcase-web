package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.LocalFoodService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local-foods")
public class LocalFoodController {

    @Resource
    private LocalFoodService localFoodService;

    // 添加美食
    @PostMapping
    public RestBean<String> addLocalFood(@RequestBody LocalFood localFood) {
        if (localFoodService.addLocalFood(localFood) > 0) {
            return RestBean.success("添加成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 删除美食
    @DeleteMapping("/{id}")
    public RestBean<String> deleteLocalFood(@PathVariable int id) {
        if (localFoodService.deleteLocalFood(id) > 0) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 更新美食
    @PutMapping("/{id}")
    public RestBean<String> updateLocalFood(@PathVariable int id, @RequestBody LocalFood localFood) {
        localFood.setId(id);
        if (localFoodService.updateLocalFood(localFood) > 0) {
            return RestBean.success("更新成功");
        } else {
            return RestBean.failure(400);
        }
    }

    // 获取指定ID的美食
    @GetMapping("/{id}")
    public RestBean<LocalFood> getLocalFood(@PathVariable int id) {
        return RestBean.success(localFoodService.getLocalFoodById(id));
    }

    // 获取所有美食
    @GetMapping
    public RestBean<List<LocalFood>> getAllLocalFoods() {
        return RestBean.success(localFoodService.getAllLocalFoods());
    }

    // 搜索美食
    @GetMapping("/search/{name}")
    public RestBean<List<LocalFood>> searchLocalFood(@PathVariable String name) {
        return RestBean.success(localFoodService.searchLocalFoodByName(name));
    }
}
