package com.hexwarrior6.shaoxingshowcasewebbackend.controller;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.RestBean;
import com.hexwarrior6.shaoxingshowcasewebbackend.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String USERNAME_REGEX = "^[A-Za-z0-9一-龥]+$";

    @Resource
    AccountService accountService;

    @PostMapping("/validate-email-register")
    public RestBean<String> validateEmailRegister(@Email @RequestParam("email") String email, HttpSession session) {
        RestBean<String> failure = detectEvilAcquire(session);
        if (failure != null) return failure;
        if (accountService.isEmailOrUsernameExist(email)) return RestBean.failure(400, "该邮箱已被注册");
        return sendValidateEmail(email, session);
    }

    @PostMapping("/validate-email-reset-password")
    public RestBean<String> validateEmail(@Email @RequestParam("email") String email, HttpSession session) {
        RestBean<String> failure = detectEvilAcquire(session);
        if (failure != null) return failure;
        if (!accountService.isEmailOrUsernameExist(email)) return RestBean.failure(400, "该邮箱尚未注册");
        return sendValidateEmail(email, session);
    }

    private static RestBean<String> detectEvilAcquire(HttpSession session) {
        String validateCodeTime = (String) session.getAttribute("validateCodeTime");
        if (session.getAttribute("validateCodeTime") != null) {
            String currentTime = String.valueOf(System.currentTimeMillis() / 60000);
            if (validateCodeTime.equals(currentTime)) {
                return RestBean.failure(400, "手速太快啦，请一分钟后重试");
            }
        }
        return null;
    }

    private RestBean<String> sendValidateEmail(@RequestParam("email") @Email String email, HttpSession session) {
        String validateCode = accountService.sendValidationEmail(email);
        if (validateCode != null) {
            session.setAttribute("email", email);
            session.setAttribute("validateCode", validateCode);
            session.setAttribute("validateCodeTime", String.valueOf(System.currentTimeMillis() / 60000));
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, "邮件发送失败，请稍后再试");
        }
    }

    @PostMapping("/register")
    public RestBean<String> register(@Pattern(regexp = USERNAME_REGEX) @Length(min = 5, max = 20) @RequestParam("username") String username, // 此处使用了@Length注解，确保输入的长度不小于5不大于20
                                     @Email @RequestParam("email") String email, // 此处使用了@Email注解，确保输入的是邮箱格式
                                     @Length(min = 5, max = 20) @RequestParam("password") String password,
                                     @RequestParam("validate_code") String validateCode,
                                     HttpSession session) {
        String sessionCode = (String) session.getAttribute("validateCode");
        String sessionEmail = (String) session.getAttribute("email");
        String validateCodeTime = (String) session.getAttribute("validateCodeTime");
        String currentTime = String.valueOf(System.currentTimeMillis() / 60000);
        if (sessionCode == null || sessionEmail == null || !sessionEmail.equals(email)) return RestBean.failure(400, "请先获取验证码");
        else if (!sessionCode.equals(validateCode)) return RestBean.failure(400, "验证码不正确");
        else if (Integer.parseInt(currentTime) > Integer.parseInt(validateCodeTime) + 5) {
            session.removeAttribute("email");
            session.removeAttribute("validateCode");
            session.removeAttribute("validateCodeTime"); // 清除垃圾数据
            return RestBean.failure(400, "验证码已过期，请重新获取");
        } else if (accountService.isUsernameExist(username)) return RestBean.failure(400, "用户名已存在");
        else {
            accountService.registerAccount(username, email, password);
            session.removeAttribute("email");
            session.removeAttribute("validateCode");
            session.removeAttribute("validateCodeTime"); // 清除垃圾数据
            return RestBean.success("注册成功");
        }
    }

    @PostMapping("reset-password-step1")
    public RestBean<String> resetPassword(@Email @RequestParam("email") String email,
                                          @RequestParam("validate_code") String validateCode,
                                          HttpSession session) {
        String sessionCode = (String) session.getAttribute("validateCode");
        String sessionEmail = (String) session.getAttribute("email");
        String validateCodeTime = (String) session.getAttribute("validateCodeTime");
        String currentTime = String.valueOf(System.currentTimeMillis() / 60000);
        if (sessionCode == null || sessionEmail == null || !sessionEmail.equals(email)) return RestBean.failure(400, "请先获取验证码");
        else if (!sessionCode.equals(validateCode)) return RestBean.failure(400, "验证码不正确");
        else if (Integer.parseInt(currentTime) > Integer.parseInt(validateCodeTime) + 5) {
            session.removeAttribute("email");
            session.removeAttribute("validateCode");
            session.removeAttribute("validateCodeTime"); // 清除垃圾数据
            return RestBean.failure(400, "验证码已过期，请重新获取");
        } else {
            session.removeAttribute("email");
            session.removeAttribute("validateCode");
            session.removeAttribute("validateCodeTime"); // 清除垃圾数据
            session.setAttribute("ValidatedEmail", email);
            return RestBean.success("邮箱验证成功");
        }
    }

    @PostMapping("reset-password-step2")
    public RestBean<String> resetPassword(@Length(min = 5, max = 20) @RequestParam("password") String password, HttpSession session) {
        String sessionValidatedEmail = (String) session.getAttribute("ValidatedEmail");
        if (sessionValidatedEmail == null) return RestBean.failure(400, "尚未验证电子邮件");
        try {
            if (accountService.resetPassword(sessionValidatedEmail, password)) {
                session.removeAttribute("ValidatedEmail");
                return RestBean.success("重置密码成功");
            } else {
                session.removeAttribute("ValidatedEmail");
                return RestBean.failure(400, "重置密码失败");
            }
        } catch (Exception e) {
            session.removeAttribute("ValidatedEmail");
            return RestBean.failure(500, "重置密码失败");
        }
    }
}
