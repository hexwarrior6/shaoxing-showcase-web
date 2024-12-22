package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;
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
    public int addLocalFood(@RequestBody LocalFood localFood) {
        return localFoodService.addLocalFood(localFood);
    }

    // 删除美食
    @DeleteMapping("/{id}")
    public int deleteLocalFood(@PathVariable int id) {
        return localFoodService.deleteLocalFood(id);
    }

    // 更新美食
    @PutMapping("/{id}")
    public int updateLocalFood(@PathVariable int id, @RequestBody LocalFood localFood) {
        localFood.setId(id);
        return localFoodService.updateLocalFood(localFood);
    }

    // 获取指定ID的美食
    @GetMapping("/{id}")
    public LocalFood getLocalFood(@PathVariable int id) {
        return localFoodService.getLocalFoodById(id);
    }

    // 获取所有美食
    @GetMapping
    public List<LocalFood> getAllLocalFoods() {
        return localFoodService.getAllLocalFoods();
    }
}
