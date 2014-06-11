package com.nanxiaoqiang.test.storm.helloworld.spouts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class WordReader implements IRichSpout {

	private static Logger logger = LogManager.getLogger(WordReader.class
			.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SpoutOutputCollector collector;

	private FileReader fileReader;

	private boolean completed = false;

	@SuppressWarnings("unused")
	private TopologyContext context;

	public boolean isDistributed() {
		return false;
	}

	public WordReader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		logger.info("OK:" + msgId);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		logger.info("activate");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		logger.info("close");

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		logger.info("deactivate");
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		logger.info("FAIL:" + msgId);
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		logger.info("nextTuple");
		/**
		 * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
		 */
		// nextTuple()会在同一个循环内被ack()和fail()周期性的调用。
		// 没有任务时它必须释放对线程的控制，其它方法才有机会得以执行。
		// 因此nextTuple的第一行就要检查是否已处理完成。
		// 如果完成了，为了降低处理器负载，会在返回前休眠一毫秒。如果任务完成了，文件中的每一行都已被读出并分发了。
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// 什么也不做
			}
			return;
		}
		String str;
		// 创建reader
		BufferedReader reader = new BufferedReader(fileReader);
		try {
			// 读所有文本行
			while ((str = reader.readLine()) != null) {
				/**
				 * 按行发布一个新值<br/>
				 * Values是一个ArrarList实现，它的元素就是传入构造器的参数。
				 */
				this.collector.emit(new Values(str), str);
			}
		} catch (Exception e) {
			logger.error("Error reading tuple", e);
			// throw new RuntimeException("Error reading tuple", e);
		} finally {
			completed = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.spout.ISpout#open(java.util.Map,
	 * backtype.storm.task.TopologyContext,
	 * backtype.storm.spout.SpoutOutputCollector)
	 */
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf,
			TopologyContext context, SpoutOutputCollector collector) {
		logger.info("open");
		// 我们将创建一个文件并维持一个collector对象<br/>
		// 第一个被调用的spout方法.
		// 参数：
		// 1、Map:conf配置对象，在定义topology对象时创建；
		// 2、TopologyContext:context，包含所有拓扑数据；
		// 3、还有SpoutOutputCollector对象，它能让我们发布交给bolts处理的数据。
		try {
			this.context = context;
			this.fileReader = new FileReader(conf.get("wordsFile").toString());
		} catch (FileNotFoundException e) {
			logger.error("找不到文件 [" + conf.get("wordFile") + "]", e);
			// throw new RuntimeException("Error reading file ["
			// + conf.get("wordFile") + "]");
		}
		this.collector = collector;
	}

	/**
	 * 声明输入域"word"
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		logger.info("declareOutputFields");
		declarer.declare(new Fields("line"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		logger.info("getComponentConfiguration");
		return null;
	}

}
