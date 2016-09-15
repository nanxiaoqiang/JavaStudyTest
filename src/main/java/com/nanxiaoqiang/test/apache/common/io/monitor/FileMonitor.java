package com.nanxiaoqiang.test.apache.common.io.monitor;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileMonitor implements FileAlterationListener {
	private static Logger logger = LogManager.getLogger(FileMonitor.class.getName());

	public FileMonitor() {
		logger.info("FileMonitor()");
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
		logger.info("FileMonitor.onStart");
	}

	@Override
	public void onDirectoryCreate(File directory) {
		logger.info("FileMonitor.onDirectoryCreate:" + directory.getName());
	}

	@Override
	public void onDirectoryChange(File directory) {
		logger.info("FileMonitor.onDirectoryChange:" + directory.getName());
	}

	@Override
	public void onDirectoryDelete(File directory) {
		logger.info("FileMonitor.onDirectoryDelete:" + directory.getName());
	}

	@Override
	public void onFileCreate(File file) {
		logger.info("FileMonitor.onFileCreate:" + file.getName());
	}

	@Override
	public void onFileChange(File file) {
		logger.info("FileMonitor.onFileChange:" + file.getName());
	}

	@Override
	public void onFileDelete(File file) {
		logger.info("FileMonitor.onFileDelete:" + file.getName());
	}

	@Override
	public void onStop(FileAlterationObserver observer) {
		logger.info("FileMonitor.onFionStop");
	}

}
