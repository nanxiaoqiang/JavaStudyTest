package com.nanxiaoqiang.test.mybatis.demo1;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	private static Logger logger = LogManager.getLogger(App.class.getName());

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();

//		Demo1 demo1 = (Demo1) session
//				.selectOne(
//						"com.nanxiaoqiang.test.mybatis.demo1.entity.Demo1Mapper.selectDemo1",
//						7000001);
		session.close();
	}

}
