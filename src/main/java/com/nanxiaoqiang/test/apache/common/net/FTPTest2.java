package com.nanxiaoqiang.test.apache.common.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FTP上传,建立文件夹
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年1月19日
 *
 */
public class FTPTest2 {
	private static Logger logger = LogManager.getLogger(FTPTest2.class
			.getName());

	private FTPClient ftpClient;

	public FTPTest2() {
		ftpClient = new FTPClient();
	}

	public void send() {
		try {
			ftpClient.connect("172.20.3.20");
			logger.debug("连接172.20.3.20");
			ftpClient.login("yhgl", "yhgl");
			logger.debug("用yhgl/yhgl登录");
			// 检验是否连接成功
			int reply = ftpClient.getReplyCode();
			logger.debug("reply:" + reply);// 成功是230
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.warn("连接被拒绝!断开连接!" + reply);
				ftpClient.disconnect();
			} else {
				// UNIX Type L8
				logger.debug("FTP系统为：" + ftpClient.getSystemType());
				// 为啥Windows Server也是UNIX？？？
				ftpClient.setKeepAlive(true);
				logger.debug("设置KeepAlive");
				// 进入目录
				if (!ftpClient.changeWorkingDirectory("/IMAGES")) {
					logger.warn("无此目录/IMAGES");
				}
				logger.info("跳转到目录/IMAGES");
				// 建立文件夹
				int mkdir = ftpClient.mkd("nxqtest");
				logger.info("创建文件返回结果:" + mkdir);// 257是创建成功
				// 进入目录
				if (!ftpClient.changeWorkingDirectory("nxqtest")) {
					logger.warn("无此目录nxqtest");
				}
				logger.info("跳转到目录/IMAGES/nxqtest");
				FileInputStream fis = null;
				fis = new FileInputStream(
						new File(
								"C:\\Users\\Administrator\\Desktop\\pics\\pics\\0001-ats.png"));
				boolean isUp = ftpClient.storeFile("0001-ats.png", fis);
				logger.info("上传文件结果:" + isUp);
				fis.close();
				ftpClient.noop();
				ftpClient.logout();
				logger.debug("登出FTP");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FTPTest2 t = new FTPTest2();
		t.send();
	}

}
