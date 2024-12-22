package com.hexwarrior6.shaoxingshowcasewebbackend.service.impl;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.Account;
import com.hexwarrior6.shaoxingshowcasewebbackend.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeService implements UserDetailsService {
    @Resource
    AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account = accountMapper.findByUsernameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}
