package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalFood;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocalFoodMapper {

    @Insert("INSERT INTO local_foods (food_name, description, ingredients, image_url, origin) " +
            "VALUES (#{foodName}, #{description}, #{ingredients}, #{imageUrl}, #{origin})")
    int insert(LocalFood localFood);

    @Delete("DELETE FROM local_foods WHERE id = #{id}")
    int deleteById(int id);

    @Update("UPDATE local_foods SET food_name = #{foodName}, description = #{description}, " +
            "ingredients = #{ingredients}, image_url = #{imageUrl}, origin = #{origin} WHERE id = #{id}")
    int update(LocalFood localFood);

    @Select("SELECT * FROM local_foods WHERE id = #{id}")
    LocalFood findById(int id);

    @Select("SELECT * FROM local_foods")
    List<LocalFood> findAll();
}

