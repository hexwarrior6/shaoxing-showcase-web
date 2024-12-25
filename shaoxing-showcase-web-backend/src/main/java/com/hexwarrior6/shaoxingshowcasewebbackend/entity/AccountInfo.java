package com.hexwarrior6.shaoxingshowcasewebbackend.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountInfo {
    private String id;
    private String username;
    private String email;
    private String avatarUrl;
}
