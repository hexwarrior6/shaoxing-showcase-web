package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalCulture;
import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocalCultureMapper {

    @Insert("INSERT INTO local_culture (culture_name, description, significance, image_url, time) " +
            "VALUES (#{cultureName}, #{description}, #{significance}, #{imageUrl}, #{time})")
    int insert(LocalCulture localCulture);

    @Delete("DELETE FROM local_culture WHERE id = #{id}")
    int deleteById(int id);

    @Update("UPDATE local_culture SET culture_name = #{cultureName}, description = #{description}, " +
            "significance = #{significance}, image_url = #{imageUrl}, time = #{time} WHERE id = #{id}")
    int update(LocalCulture localCulture);

    @Select("SELECT * FROM local_culture WHERE id = #{id}")
    LocalCulture findById(int id);

    @Select("SELECT * FROM local_culture")
    List<LocalCulture> findAll();

    @Select("SELECT * FROM local_culture WHERE culture_name LIKE CONCAT('%', #{name}, '%')")
    List<LocalCulture> searchByName(String name);
}