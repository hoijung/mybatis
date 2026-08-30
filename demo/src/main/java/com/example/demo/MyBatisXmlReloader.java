package com.example.demo;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class MyBatisXmlReloader {

	private final SqlSessionFactory sqlSessionFactory;

	public MyBatisXmlReloader(
//            @Qualifier("cssSqlSessionFactory")
			SqlSessionFactory sqlSessionFactory) {

		this.sqlSessionFactory = sqlSessionFactory;
	}

	public SqlSessionFactory reload(DataSource dataSource) throws Exception {

		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

		factory.setDataSource(dataSource);

		Resource config = new ClassPathResource("mapper/mybatis-config.xml");

		factory.setConfigLocation(config);

		System.out.println("[MyBatis XML RELOAD] ");

		return factory.getObject();
	}

	public void reload(String xmlPath) throws Exception {

		Configuration configuration = sqlSessionFactory.getConfiguration();

		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml");

		

		for (Resource sub : resources) {
			
			String path = sub.getFile().getPath();

			if ("C:\\Users\\user\\eclipse-workspace\\demo\\bin\\main\\mapper\\mybatis-config.xml".equals(path)) {
				continue;
			}
			System.out.println("[MyBatis XML RELOAD] " + path) ;
			
			this.removeMappedStatements(configuration, path);

			// 2. 기존 XML 로딩 기록 삭제
			removeLoadedResource(configuration, path);
			
			

			try (InputStream inputStream = sub.getInputStream()) {

				XMLMapperBuilder parser = new XMLMapperBuilder(inputStream, configuration, path,
						configuration.getSqlFragments());

				parser.parse();
			}
		}

//		System.out.println("[MyBatis XML RELOAD 완료] " + xmlPath);
	}

	private void removeMappedStatements(Configuration configuration, String namespace) {

		List<String> removeIds = new ArrayList<>();

		for (MappedStatement ms : configuration.getMappedStatements()) {
			System.out.println(ms.getId());
			// XML에 등록된 MappedStatement만 확인
//            if (ms.getId().startsWith(namespace + ".")) {
			System.out.println(ms.getId());
			removeIds.add(ms.getId());
//            }
		}

		for (String id : removeIds) {

			configuration.getMappedStatements().removeIf(ms -> ms instanceof MappedStatement && ms.getId().equals(id));

			System.out.println("[MyBatis REMOVE] " + id);
		}
	}

	private void removeLoadedResource(Configuration configuration, String resource) throws Exception {

		Field field = Configuration.class.getDeclaredField("loadedResources");

		field.setAccessible(true);

		Set<String> loadedResources = (Set<String>) field.get(configuration);

		loadedResources.remove(resource);

		System.out.println("[REMOVE loadedResources] " + resource);
	}
}