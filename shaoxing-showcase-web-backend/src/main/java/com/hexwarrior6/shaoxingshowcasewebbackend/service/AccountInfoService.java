package com.hexwarrior6.shaoxingshowcasewebbackend.service;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;

public interface AccountInfoService {
    AccountInfo getAccountInfoByUsernameOrEmail(String usernameOrEmail);

    int updateAccount(AccountInfo account);

    int deleteAccount(int id);
}
