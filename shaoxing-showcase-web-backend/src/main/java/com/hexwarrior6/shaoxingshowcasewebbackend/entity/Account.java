package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Account {
    private int id;
    private String username;
    private String password;
    private String email;
}
