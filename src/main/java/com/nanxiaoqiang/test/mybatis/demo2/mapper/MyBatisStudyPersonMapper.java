package com.nanxiaoqiang.test.mybatis.demo2.mapper;

import java.util.List;

import com.nanxiaoqiang.test.mybatis.demo2.entity.MyBatisStudyPerson;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年2月23日
 *
 */
public interface MyBatisStudyPersonMapper {

	List<MyBatisStudyPerson> selectAllPersons();

	MyBatisStudyPerson selectPersonsById(Integer id);

	void insertPerson(MyBatisStudyPerson p);

	void updatePerson(MyBatisStudyPerson p);
}
