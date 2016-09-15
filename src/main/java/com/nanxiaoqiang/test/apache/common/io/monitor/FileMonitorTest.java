package com.nanxiaoqiang.test.apache.common.io.monitor;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 文件变化监控
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年9月16日
 */
public class FileMonitorTest {
	private static Logger logger = LogManager.getLogger(FileMonitorTest.class.getName());

	public FileMonitorTest() {
	}

	public static void main(String[] args) throws Exception {
		
		String path = "src/main/resources/apache/commons/io/filemonitor";
		File f = new File(path);
		logger.info(f.getAbsolutePath());
		FileAlterationMonitor monitor = new FileAlterationMonitor(5000); 
		FileMonitor fileMonitor = new FileMonitor();
		FileAlterationObserver observer = new FileAlterationObserver(new File(path));
		monitor.addObserver(observer);
		observer.addListener(fileMonitor);
		monitor.start();
	}

}
