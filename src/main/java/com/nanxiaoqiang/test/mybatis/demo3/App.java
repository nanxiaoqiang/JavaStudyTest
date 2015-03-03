package com.nanxiaoqiang.test.mybatis.demo3;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.mybatis.demo3.entity.MyBatisStudyPerson;
import com.nanxiaoqiang.test.mybatis.demo3.mapper.MyBatisStudyPersonMapper;

public class App {

	private static Logger logger = LogManager.getLogger(App.class.getName());

	String resource = "mybatis/demo3/mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory = null;

	public void init() {
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// .build(inputStream, "development");
		// 这里填写的是配置文件mybatis-config.xml的environment的id，可用于多数据源
	}

	public App() {
		logger.info("初始化SqlSessionFactory");
		init();
	}

	/**
	 * 动态更新，在mapper的xml中set底下的句子中最后一定要加上","
	 */
	public void updateTest() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			MyBatisStudyPersonMapper mapper = session
					.getMapper(MyBatisStudyPersonMapper.class);
			MyBatisStudyPerson p = mapper.selectPersonsById(2);

			p.setName("Global");
			p.setSex(null);// 不更新
			p.setTel("55443322-1234");
			p.setPhone("13344556677");
			p.setAddress(null);// 不更新
			p.setInfo("good choice");

			mapper.updatePerson(p);
			session.commit();// 一定要提交!
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		App app = new App();
		app.updateTest();
	}

}
