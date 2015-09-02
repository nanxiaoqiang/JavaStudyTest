package com.nanxiaoqiang.test.javastudytest.io;

import java.io.File;

public class FileTest {

	public static void main(String[] args) {
		String path = "c:/asd/bbb/ccc";
		// String file_path = "c:/aasd/bfbb/czcc";
		String file_path = path + "/asd";

		File dir = new File(path);
		if (!(dir.exists() & dir.isDirectory())) {
			boolean bc = dir.mkdirs();
			System.out.println(bc);
		}
		File f = new File(path, "asd");
		System.out.println(file_path);
		if (!(f.exists() & f.isFile())) {
			// boolean b = false;
			try {
				boolean b = dir.createNewFile();
				System.out.println(b);
			} catch (Throwable e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		dir.delete();
	}
}
