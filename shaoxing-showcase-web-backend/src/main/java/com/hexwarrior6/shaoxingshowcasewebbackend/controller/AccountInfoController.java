package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.AccountInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class AccountInfoController {
    @Resource
    AccountInfoService accountInfoService;

    @GetMapping("/me")
    public RestBean<AccountInfo> getMe(@SessionAttribute("accountInfo") AccountInfo accountInfo) {
        return RestBean.success(accountInfo);
    }

    @PostMapping("/me")
    public RestBean<AccountInfo> updateAccount(@RequestBody AccountInfo accountInfo) {
        int result = accountInfoService.updateAccount(accountInfo);
        if (result == 1) {
            return RestBean.success(accountInfo);
        } else {
            return RestBean.failure(400, "更新失败");
        }
    }

    @DeleteMapping("/me")
    public RestBean<String> deleteAccount(@SessionAttribute("accountInfo") AccountInfo accountInfo) {
        int result = accountInfoService.deleteAccount(Integer.parseInt(accountInfo.getId()));
        if (result > 0) {
            return RestBean.success("删除成功");
        } else {
            return RestBean.failure(400, "删除失败");
        }
    }
}
