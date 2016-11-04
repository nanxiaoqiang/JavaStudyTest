package com.nanxiaoqiang.test.javastudytest.lang.base;

public class EunmValueNameTest {

	public static enum NameValue {
		NEGATIVE_TWO(-2, "不通过"), 
		NEGATIVE_ONE(-1, "不知道"), 
		ZERO(0, "刚刚好"), 
		PAUSE(1, "暂停"), 
		NORMAL(2, "正常");

		private Integer value;
		private String name;

		NameValue(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public static String getEnumName(Integer value) {
			for (NameValue nameValue : NameValue.values()) {
				if (nameValue.getValue().intValue() == value.intValue()) {
					return nameValue.getName() + "[" + value + "]";
				}
			}
			return "null[" + value + "]";
		}
	}

	public static void main(String[] args) {
		for (NameValue nameValue : EunmValueNameTest.NameValue.values()) {
			System.out.println(nameValue.name() + "|" + nameValue.ordinal() + "|" + nameValue.getName() + "|" +nameValue.getValue());
		}
		System.out.println("**************************");
		System.out.println(EunmValueNameTest.NameValue.getEnumName(-2));
		System.out.println(EunmValueNameTest.NameValue.getEnumName(101));
	}

}
