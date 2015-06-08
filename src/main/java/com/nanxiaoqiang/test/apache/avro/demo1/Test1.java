package com.nanxiaoqiang.test.apache.avro.demo1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import com.nanxiaoqiang.test.apache.avro.java.User;

public class Test1 {

	public static void main(String[] args) throws IOException {
		User user1 = new User();
		user1.setName("nanxiaoqiang");
		user1.setFavoriteNumber(19850226);
		user1.setFavoriteColor("violet");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 不再需要传schema了，直接用StringPair作为范型和参数，
		DatumWriter<User> writer = new SpecificDatumWriter<User>(User.class);
		Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(user1, encoder);
		encoder.flush();
		out.close();

		DatumReader<User> reader = new SpecificDatumReader<User>(User.class);
		Decoder decoder = DecoderFactory.get().binaryDecoder(out.toByteArray(),
				null);
		User result = reader.read(null, decoder);
		System.out.println(result.getName() + "|" + result.getFavoriteNumber()
				+ "|" + result.getFavoriteColor());
	}

}
