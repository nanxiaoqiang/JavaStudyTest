package com.nanxiaoqiang.test.mybatis.demo3.mapper;

import java.util.List;

import com.nanxiaoqiang.test.mybatis.demo3.entity.MyBatisStudyPerson;

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

	List<MyBatisStudyPerson> selectAllPersons(MyBatisStudyPerson p);

	MyBatisStudyPerson selectPersonsById(Integer id);

	void insertPerson(MyBatisStudyPerson p);

	void updatePerson(MyBatisStudyPerson p);
}
