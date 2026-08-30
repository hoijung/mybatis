package com.example.demo;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService2 {

    @Autowired
    private MyBatisXmlReloader xmlReloader;

    @Autowired
//    @Qualifier("cssDataSource")
    private DataSource dataSource;

    private SqlSessionTemplate sqlSessionTemplate;

    public void reload() throws Exception {

        SqlSessionFactory factory =
            xmlReloader.reload(dataSource);

        sqlSessionTemplate =
            new SqlSessionTemplate(factory);

        System.out.println("MyBatis Reload 완료");
    }

    public List<Map<String, Object>> selectUser() {

        return sqlSessionTemplate.selectList(
            "com.example.demo.mapper.UserMapper.selectUser"
        );
    }
}
