package com.nanxiaoqiang.test.strman;

import static strman.Strman.append;
import static strman.Strman.appendArray;
import static strman.Strman.at;
import static strman.Strman.between;
import static strman.Strman.chars;
import static strman.Strman.collapseWhitespace;
import static strman.Strman.contains;

public class StrmanTest {

	public static void main(String[] args) {
		System.out.println(append("h", "e", "l", "l", "o"));
		// annpendArray String append String array to String
		System.out.println(appendArray("world", new String[]{"hello", "strman"}));
		// start with 0, return Optional
		System.out.println(at("hello", 1));
		System.out.println(at("hello", 5));// Optional.empty
		// 拆分成数组，用的是正则，所以第二个第三个参数的start和end必须对应，否则会报java.util.regex.PatternSyntaxException
		System.out.println(between("[q][da][sd][bd][gwe]", "[", "]").length);
		// 返回的是拆分成Char的String数组，空格和换行同样处理
		System.out.println(chars("hel  \t\r\nlo ").length);
		// 不论是多少空白符，只会缩减为一个，包括换行
		System.out.println(collapseWhitespace("hel  \t\r\nlo "));
		// contains
		System.out.println(contains("AbcDefg", "cDe"));
		System.out.println(contains("AbcDefg", "cde", true));// 最后一个参数是区分大小写
		System.out.println(contains("AbcDefg", "cde", false));
	}

}
