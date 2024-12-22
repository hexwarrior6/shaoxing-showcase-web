package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/user")
public class AccountInfoController {

    @GetMapping("/me")
    public RestBean<AccountInfo> getMe(@SessionAttribute("accountInfo") AccountInfo accountInfo) {
        return RestBean.success(accountInfo);
    }
}
