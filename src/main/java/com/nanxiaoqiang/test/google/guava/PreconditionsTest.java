package com.nanxiaoqiang.test.google.guava;

import com.google.common.base.Preconditions;
/**
 * 
 * @ClassName: PreconditionsTest
 * @Description: Guava的测试
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年7月24日 上午00:02:48
 *
 */
public class PreconditionsTest {

	public PreconditionsTest() {
	}

	public static void main(String[] args) {
		try {
			saySomething(0, "");
		} catch (IllegalArgumentException iae) {
			System.out.println("hehe, IllegalArgumentException:"
					+ iae.getMessage());
		}
		try {
			saySomething(12, null);
		} catch (NullPointerException npe) {
			System.out.println("hehe NullPointerException:" + npe.getMessage());
		}
	}

	private static void saySomething(int age, String name) {
		Preconditions.checkArgument(age > 0, "age must more than 0.");// 后边的参数是抛出异常的errorMessage
		Preconditions.checkNotNull(name, "name can not be null.");
		System.out.println("age is " + age + ", name is " + name);
	}

}
