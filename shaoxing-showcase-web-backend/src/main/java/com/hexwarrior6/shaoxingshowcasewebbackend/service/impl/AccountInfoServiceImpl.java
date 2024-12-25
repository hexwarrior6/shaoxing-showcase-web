package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.AccountMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.AccountInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    AccountMapper accountMapper;

    @Override
    public AccountInfo getAccountInfoByUsernameOrEmail(String usernameOrEmail) {
        return accountMapper.findInfoByUsernameOrEmail(usernameOrEmail);
    }

    @Override
    public int updateAccount(AccountInfo account) {
        return accountMapper.UpdateById(account);
    }

    @Override
    public int deleteAccount(int id) {
        return accountMapper.DeleteAccountById(id);
    }
}
