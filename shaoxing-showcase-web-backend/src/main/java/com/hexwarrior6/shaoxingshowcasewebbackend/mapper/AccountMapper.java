package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.Account;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM account WHERE username = #{username} OR email = #{email}")
    Account findByUsernameOrEmail(String usernameOrEmail);

    @Insert("INSERT INTO account (username, password, email) VALUES (#{username}, #{password}, #{email})")
    void insertAccount(Account account);

    @Select("SELECT * FROM account WHERE username = #{username}")
    Account findByUsername(String username);

    @Update("UPDATE account SET password = #{password} WHERE email = #{email}")
    int updatePasswordByEmail(Account account);

    @Select("SELECT * FROM account WHERE username = #{username} OR email = #{email}")
    AccountInfo findInfoByUsernameOrEmail(String usernameOrEmail);

    @Update("UPDATE account SET avatarUrl = #{avatarUrl}, username=#{username}, id=#{id} , email = #{email} WHERE id = #{id}")
    int UpdateById(AccountInfo accountInfo);

    @Delete("DELETE FROM account WHERE id = #{id}")
    int DeleteAccountById(int id);
}
