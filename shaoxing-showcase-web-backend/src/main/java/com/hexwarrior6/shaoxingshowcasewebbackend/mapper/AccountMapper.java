package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.Account;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.AccountInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM db_account WHERE username = #{username} OR email = #{email}")
    Account findByUsernameOrEmail(String usernameOrEmail);

    @Select("SELECT * FROM db_account WHERE username = #{username} OR email = #{email}")
    AccountInfo findInfoByUsernameOrEmail(String usernameOrEmail);
    @Insert("INSERT INTO db_account (username, password, email) VALUES (#{username}, #{password}, #{email})")
    void insertAccount(Account account);

    @Select("SELECT * FROM db_account WHERE username = #{username}")
    Account findByUsername(String username);

    @Update("UPDATE db_account SET password = #{password} WHERE email = #{email}")
    int updateAccountByEmail(Account account);
}
