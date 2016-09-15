package com.nanxiaoqiang.test.apache.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOUtilsTest {

	private static Logger logger = LogManager.getLogger(IOUtilsTest.class.getName());

	public IOUtilsTest() {
	}

	public static void main(String[] args) {
		try {
			InputStream in = new URL("http://commons.apache.org").openStream();
			logger.info(IOUtils.toString(in, Charset.forName("utf-8")));
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}

}
