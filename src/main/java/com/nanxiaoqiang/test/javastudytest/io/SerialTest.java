package com.nanxiaoqiang.test.javastudytest.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Java序列化
 * @author nanxiaoqiang
 *
 */
public class SerialTest {

	public static void main(String[] args) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("result.obj"));
			out.writeBoolean(true);
			out.writeLong(1234567890L);
			out.writeObject("Hello 南肖墙");
			out.close();
			
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
					"result.obj"));
			boolean b = oin.readBoolean();
			long l = oin.readLong();
			String str = (String) oin.readObject();
			oin.close();
			
			System.out.println(String.format("[%s][%s][%s]", b, l, str));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
