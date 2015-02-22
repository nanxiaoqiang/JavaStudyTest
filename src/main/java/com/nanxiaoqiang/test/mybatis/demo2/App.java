package com.nanxiaoqiang.test.mybatis.demo2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.mybatis.demo2.entity.MyBatisStudyPerson;
import com.nanxiaoqiang.test.mybatis.demo2.mapper.MyBatisStudyPersonMapper;

public class App {
	private static Logger logger = LogManager.getLogger(App.class.getName());

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String resource = "mybatis/demo2/mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			MyBatisStudyPersonMapper mapper = session
					.getMapper(MyBatisStudyPersonMapper.class);
			// // insert模块
			// MyBatisStudyPerson p = new MyBatisStudyPerson("name", 1,
			// "4427881",
			// "13512341234", "address", "infoinfo");
			// mapper.insertPerson(p);
			// session.commit();
			// logger.info("insert 成功：" + p);

			 // insert模块2
			// MyBatisStudyPerson p1 = new MyBatisStudyPerson("Bob", 0,
			// "5141245",
			// "13888889999", "homeless", "noid");
			// mapper.insertPerson(p1);
			// session.commit();
			// logger.info("insert 成功：" + p1);// 此处并不会返回数据库对ID的赋值。

			// get模块
			MyBatisStudyPerson p3 = mapper.selectPersonsById(1);
			if (p3 != null) {
				// 有问题，Text的info显示不出来！
				// 后来在xml的配置中更改了infomation as info就好了
				logger.info(p3);
			}

			// getList模块
			List<MyBatisStudyPerson> all = mapper.selectAllPersons();
			if (all != null) {
				logger.info(all);
			}
		} finally {
			session.close();
		}
	}

}
