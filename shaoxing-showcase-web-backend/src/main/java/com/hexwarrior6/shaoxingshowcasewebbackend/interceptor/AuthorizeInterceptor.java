package com.hexwarrior6.shaoxingshowcasewebbackend.interceptor;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.AccountMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizeInterceptor implements HandlerInterceptor {
    @Resource
    AccountMapper accountMapper;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        AccountInfo accountInfo = accountMapper.findInfoByUsernameOrEmail(username);
        request.getSession().setAttribute("accountInfo", accountInfo);
        return true;
    }
}
