package com.hexwarrior6.shaoxingshowcasewebbackend.service;

public interface AccountService {
    void registerAccount(String username, String email, String password);

    Boolean resetPassword(String username, String password);

    String sendValidationEmail(String email);

    Boolean isEmailOrUsernameExist(String email);

    Boolean isUsernameExist(String username);
}
