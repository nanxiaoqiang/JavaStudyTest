package com.nanxiaoqiang.test.javastudytest.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;

/**
 * 
 * @ClassName: BigFileReadTest
 * @Description: 读取大文件长度
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年9月9日 下午12:29:30
 *
 */
public class BigFileReadTest {

	public BigFileReadTest() {
	}

	public static void main(String[] args) throws Exception {
		File file = new File("C:/Users/Administrator/Desktop/20150908"); // 12000M
		ReadLastLine(file);
		ReadLines3(file);// Better
		ReadLines2(file);
		// ReadLines1(file); // really bad method
	}

	private static void ReadLines3(File file) throws Exception {
		long linestart = System.currentTimeMillis();
		int lines = getFileLineNumber(file);
		long longdelt = System.currentTimeMillis() - linestart;
		System.out.println("文件有" + lines + "行");
		System.out.println("读取时间(毫秒):" + longdelt);
	}

	private static void ReadLines2(File file) throws IOException {
		long linestart = System.currentTimeMillis();
		readLinesFromFile(file);
		long longdelt = System.currentTimeMillis() - linestart;
		System.out.println("读取时间(毫秒):" + longdelt);
	}

	public static void ReadLines1(File file) throws IOException {
		long linestart = System.currentTimeMillis();
		int lines = readLines(file);
		long longdelt = System.currentTimeMillis() - linestart;
		System.out.println("文件有" + lines + "行");
		System.out.println("读取时间(毫秒):" + longdelt);
	}

	private static void ReadLastLine(File file) throws IOException {
		long start = System.currentTimeMillis();
		String lastLine = readLastLine(file, "gbk");
		long delt = System.currentTimeMillis() - start;
		System.out.println(lastLine);
		System.out.println("读取时间(毫秒):" + delt);
	}

	private static int readLines(File file) throws IOException {
		int length = 0;
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return length;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			while (raf.readLine() != null) {
				length++;
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}
		return length;
	}

	private static int getFileLineNumber(File file) throws Exception {
		LineNumberReader lineNumberReader = new LineNumberReader(
				new FileReader(file));
		// it will return the number of characters actually skipped
		lineNumberReader.skip(Long.MAX_VALUE);

		int lineNumber = lineNumberReader.getLineNumber();

		lineNumber++;

		lineNumberReader.close();

		return lineNumber;
	}

	private static void readLinesFromFile(File file) {

		LineNumberReader lineNumberReader = null;
		int length = 0;
		try {

			// 构造LineNumberReader实例
			lineNumberReader = new LineNumberReader(new FileReader(file));

			String line = null;
			while ((line = lineNumberReader.readLine()) != null) {
				System.out.println("Line " + lineNumberReader.getLineNumber()
						+ ": " + line);
				length++;
			}
			System.out.println(length);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// 关闭lineNumberReader
			try {
				if (lineNumberReader != null) {
					lineNumberReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static String readLastLine(File file, String charset)
			throws IOException {
		if (!file.exists() || file.isDirectory() || !file.canRead()) {
			return null;
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			long len = raf.length();
			System.out.println("文件大小" + len);
			if (len == 0L) {
				return "";
			} else {
				long pos = len - 1;
				while (pos > 0) {
					pos--;
					raf.seek(pos);
					if (raf.readByte() == '\n') {
						break;
					}
				}
				if (pos == 0) {
					raf.seek(0);
				}
				byte[] bytes = new byte[(int) (len - pos)];
				raf.read(bytes);
				if (charset == null) {
					return new String(bytes);
				} else {
					return new String(bytes, charset);
				}
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}
}
