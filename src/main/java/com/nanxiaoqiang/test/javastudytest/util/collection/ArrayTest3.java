package com.nanxiaoqiang.test.javastudytest.util.collection;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ArrayTest3 {
	private Long id;

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayTest3(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ArrayTest3() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public static void main(String[] args) {
		String[] aaa = new String[] { "aa", "bb", "cc" };
		System.out.println(aaa);
		ArrayTest3 at1 = new ArrayTest3(1L, "11a");
		ArrayTest3 at2 = new ArrayTest3(2L, "22a");
		ArrayTest3 at3 = new ArrayTest3(3L, "33a");
		ArrayTest3 at4 = new ArrayTest3(4L, "44a");
		ArrayTest3 at5 = new ArrayTest3(5L, "55a");
		List<ArrayTest3> array = new ArrayList<>();
		array.add(at1);
		array.add(at2);
		array.add(at3);
		array.add(at4);
		array.add(at5);
		Object[] aa1 = array.toArray();
		ArrayTest3[] aa2 = array.toArray(new ArrayTest3[array.size()]);// 泛型安全的转换，瞎胡设置会ArrayStoreException
		System.out.println(aa1);
		for (Object at : aa1) {
			System.out.println(at);
		}
		System.out.println(aa2);
		for (ArrayTest3 at : aa2) {
			System.out.println(at);
		}
	}
}
