package com.nanxiaoqiang.test.mybatis.demo1;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.mybatis.demo1.entity.Demo1;
import com.nanxiaoqiang.test.mybatis.demo1.entity.Demo1Mapper;

/**
 * 最简单的MyBatis的例子。<br/>
 * 需要读取的是mybatis/mybatis-config.xml文件<br/>
 * 采用的注记，没有用XML。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月28日
 * 
 */
public class App {
	private static Logger logger = LogManager.getLogger(App.class.getName());

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String resource = "mybatis/demo1/mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		Demo1Mapper mapper = session.getMapper(Demo1Mapper.class);
		Demo1 demo1 = mapper.selectDemo1(70101000000L);
		// Demo1 demo1 = (Demo1) session
		// .selectOne(
		// "com.nanxiaoqiang.test.mybatis.demo1.entity.Demo1Mapper.selectDemo1",
		// 7000001);
		logger.info(demo1.getId() + "|" + demo1.getSystem() + "|"
				+ demo1.getStationname() + "|"
				+ demo1.getFinal_point_description());
		session.close();
	}
}
