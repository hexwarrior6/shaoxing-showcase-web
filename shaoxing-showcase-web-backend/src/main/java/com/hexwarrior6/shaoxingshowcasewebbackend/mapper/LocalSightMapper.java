package com.hexwarrior6.shaoxingshowcasewebbackend.mapper;

import com.hexwarrior6.shaoxingshowcasewebbackend.entity.LocalSight;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LocalSightMapper {

    @Insert("INSERT INTO local_sights (sight_name, location, description, image_url, opening_hours) " +
            "VALUES (#{sightName}, #{location}, #{description}, #{imageUrl}, #{openingHours})")
    int insert(LocalSight localSight);

    @Delete("DELETE FROM local_sights WHERE id = #{id}")
    int deleteById(int id);

    @Update("UPDATE local_sights SET sight_name = #{sightName}, location = #{location}, " +
            "description = #{description}, image_url = #{imageUrl}, opening_hours = #{openingHours} WHERE id = #{id}")
    int update(LocalSight localSight);

    @Select("SELECT * FROM local_sights WHERE id = #{id}")
    LocalSight findById(int id);

    @Select("SELECT * FROM local_sights")
    List<LocalSight> findAll();
}
