package com.nanxiaoqiang.test.apache.common.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 读取下载FTP文件的例子
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年7月14日
 * 
 */
public class FtpTest {
	private static Logger logger = LogManager
			.getLogger(FtpTest.class.getName());

	// private String localFileName;
	private FTPClient ftpClient;

	public FtpTest() {
		ftpClient = new FTPClient();
	}

	public void downloadFiles() {
		try {
			ftpClient.connect("10.189.0.21");
			logger.debug("连接10.189.0.21");
			ftpClient.login("root", "root");
			logger.debug("用root/root登录");
			// 检验是否连接成功
			int reply = ftpClient.getReplyCode();
			logger.debug("reply:" + reply);// 成功是230
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.warn("连接被拒绝!断开连接!" + reply);
				ftpClient.disconnect();
			} else {
				logger.debug("FTP系统为：" + ftpClient.getSystemType());
				// UNIX Type: L8 Version: BSD-44
				ftpClient.setKeepAlive(true);
				logger.debug("设置KeepAlive");
				// 进入目录
				if (!ftpClient.changeWorkingDirectory("/ftpsrv/ccs")) {
					logger.warn("无此目录/ftpsrv/ccs");
				}
				ftpClient.changeWorkingDirectory("/ftpsrv/cc");
				logger.debug("进入目录/ftpsrv/cc");
				ftpClient.setBufferSize(1024);
				logger.debug("设置buffer size 1024");
				logger.debug("System file encoding is "
						+ System.getProperty("file.encoding"));
				ftpClient.setControlEncoding("iso-8859-1");
				logger.debug("设置encoding iso-8859-1");

				OutputStream output;
				// 中文的有问题哦！！！！
				output = new FileOutputStream("C:/TCC.txt");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				logger.debug("设置读取文件类型为二进制");
				ftpClient.retrieveFile("强制终止TCC代理.txt", output);
				logger.debug("下载文件");

				output.close();
				logger.debug("关闭文件");
				// 全英文数字的文件没问题
				OutputStream output2;

				output2 = new FileOutputStream("C:/asdasdasd.png");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				logger.debug("设置读取文件类型为二进制");
				ftpClient.retrieveFile("asdasdasd.png", output2);
				logger.debug("下载文件asdasdasd.png");
				output2.close();
				logger.debug("关闭文件2");

				// 一个稍微大一点的文件，大概有20M
				OutputStream output3;

				output3 = new FileOutputStream("C:/cs.mdb");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				logger.debug("设置读取文件类型为二进制");
				ftpClient.retrieveFile("cs.mdb", output3);
				logger.debug("下载大文件cs.mdb");
				output3.close();
				logger.debug("关闭大文件");

				ftpClient.noop();
				ftpClient.logout();
				logger.debug("登出FTP");
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
					logger.debug("断开FTP连接");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FtpTest ftp = new FtpTest();
		// 下载文件
		ftp.downloadFiles();
		// 队列文件
		ftp.listFiles();
	}

	private void listFiles() {
		try {
			ftpClient.connect("10.189.0.21");
			logger.debug("连接10.189.0.21");
			ftpClient.login("root", "root");
			logger.debug("用root/root登录");
			// 检验是否连接成功
			int reply = ftpClient.getReplyCode();
			logger.debug("reply:" + reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.warn("连接被拒绝!断开连接!");
				ftpClient.disconnect();
			}
			// 进入目录
			ftpClient.changeWorkingDirectory("/ftpsrv/cc");
			logger.debug("进入目录/ftpsrv/cc");
			ftpClient.setBufferSize(1024);
			logger.debug("设置buffer size 1024");
			ftpClient.setControlEncoding("GBK");
			logger.debug("设置encoding GBK");
			FTPFile[] files = ftpClient.listFiles();
			for (FTPFile f : files) {
				logger.debug(f.getName() + " | "
						+ (f.isDirectory() ? "d" : "f") + " | " + f.getSize());
			}

			ftpClient.noop();
			ftpClient.logout();
			logger.debug("登出FTP");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
					logger.debug("断开FTP连接");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
