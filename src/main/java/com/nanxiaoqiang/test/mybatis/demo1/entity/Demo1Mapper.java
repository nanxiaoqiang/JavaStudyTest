package com.nanxiaoqiang.test.mybatis.demo1.entity;

import org.apache.ibatis.annotations.Select;

public interface Demo1Mapper {

	@Select("SELECT id, stationname, system, final_point_description FROM bjmetro_l7_iscs_dic_v WHERE id = #{id}")
	Demo1 selectDemo1(long id);

}
