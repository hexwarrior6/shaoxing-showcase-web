package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.Account;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.AccountMapper;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerAccount(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        accountMapper.insertAccount(new Account().setUsername(username).setEmail(email).setPassword(encodedPassword));
    }

    @Override
    public Boolean resetPassword(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return accountMapper.updatePasswordByEmail(new Account().setEmail(email).setPassword(encodedPassword)) == 1;
    }

    @Override
    public String sendValidationEmail(String email) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ygwyy.top/邮箱/index.php"))
                .method("POST", HttpRequest.BodyPublishers.ofString("email=" + email + "&username=" + email))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 假设 response 是你的 HTTP 响应对象
        String responseBody = response.body();  // 获取响应的 JSON 字符串

//        // 示例 JSON 字符串
//        String responseBody = "{\"success\":true,\"message\":\"592187\"}";
        // 将 JSON 字符串解析为 JSONObject 对象
        JSONObject jsonResponse = JSON.parseObject(responseBody);
        // 通过 key 提取具体的字段
        boolean success = jsonResponse.getBooleanValue("success");  // 获取 success 字段的值
        if (success) {
            String code = jsonResponse.getString("message");  // 获取 message 字段的值
            System.out.println(email + "的验证码：" + code);
            return code;
        } else {
            return null;
        }
    }

    @Override
    public Boolean isEmailOrUsernameExist(String email) {
        Account account = accountMapper.findByUsernameOrEmail(email);
        return account != null;
    }

    @Override
    public Boolean isUsernameExist(String username) {
        Account account = accountMapper.findByUsername(username);
        return account != null;
    }
}
