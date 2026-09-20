package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.StaticsMapper;

@Service
public class StaticsService {

    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";

    private final StaticsMapper userMapper;

    public StaticsService(StaticsMapper userMapper) {

        this.userMapper = userMapper;
    }

    public List<Map<String, Object>> selectUser(String startDate, String endDate) {
        return selectUser(startDate, endDate, null, null);
    }

    public List<Map<String, Object>> selectUser(String startDate, String endDate, String distributeYn) {
        return selectUser(startDate, endDate, distributeYn, null);
    }

    public List<Map<String, Object>> selectUser(String startDate, String endDate, String distributeYn, String existOrderYn) {
        Map<String, Object> param = new HashMap<>();

        param.put(START_DATE, startDate);
        param.put(END_DATE, endDate);
        param.put("distributeYn", distributeYn);
        param.put("existOrderYn", existOrderYn);

        return userMapper.selectUser(param);
    }

    public List<Map<String, Object>> selectProd(String startDate, String endDate) {
        return selectProd(startDate, endDate, null, null, 4);
    }

    public List<Map<String, Object>> selectProd(String startDate, String endDate, String distributeYn) {
        return selectProd(startDate, endDate, distributeYn, null, 4);
    }

    public List<Map<String, Object>> selectProd(String startDate, String endDate, String distributeYn, String existOrderYn) {
        return selectProd(startDate, endDate, distributeYn, existOrderYn, 4);
    }

    public List<Map<String, Object>> selectProd(String startDate, String endDate, String distributeYn, String existOrderYn, Integer etcThreshold) {
        Map<String, Object> param = new HashMap<>();

        int threshold = (etcThreshold == null || etcThreshold < 0) ? 4 : etcThreshold;

        param.put(START_DATE, startDate);
        param.put(END_DATE, endDate);
        param.put("distributeYn", distributeYn);
        param.put("existOrderYn", existOrderYn);
        param.put("etcThreshold", threshold);

        return userMapper.selectProd(param);
    }

    public List<Map<String, Object>> selectTeam(String startDate, String endDate, String distributeYn, String existOrderYn) {
        Map<String, Object> param = new HashMap<>();

        param.put(START_DATE, startDate);
        param.put(END_DATE, endDate);
        param.put("distributeYn", distributeYn);
        param.put("existOrderYn", existOrderYn);

        return userMapper.selectTeam(param);
    }

    public List<Map<String, Object>> selectState(String startDate, String endDate, String stateType) {
        return selectState(startDate, endDate, stateType, null, null);
    }

    public List<Map<String, Object>> selectState(String startDate, String endDate, String stateType, String distributeYn, String existOrderYn) {
        Map<String, Object> param = new HashMap<>();

        param.put(START_DATE, startDate);
        param.put(END_DATE, endDate);
        param.put("stateType", stateType);
        param.put("distributeYn", distributeYn);
        param.put("existOrderYn", existOrderYn);

        return userMapper.selectState(param);
    }
}
