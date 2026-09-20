package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface StaticsMapper {

    List<Map<String, Object>> selectUser(Map<String, Object> param);

    List<Map<String, Object>> selectProd(Map<String, Object> param);

    List<Map<String, Object>> selectTeam(Map<String, Object> param);

    List<Map<String, Object>> selectState(Map<String, Object> param);
}