package com.nanxiaoqiang.test.storm.helloworld.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

/**
 * 负责为单词计数
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月11日
 * 
 */
public class WordCounter implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(WordCounter.class
			.getName());

	Integer id;
	String name;
	Map<String, Integer> counters;
	private OutputCollector collector;

	public WordCounter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		// 这个spout结束时（集群关闭的时候），我们会显示单词数量
		logger.info("cleanup");
		logger.info("-- 单词数 【" + name + "-" + id + "】 --");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			logger.info(entry.getKey() + ": " + entry.getValue());
		}
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		logger.info("execute");
		// 为每个单词计数
		String str = input.getString(0);
		// 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		// 对元组作为应答
		collector.ack(input);
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map conf,
			TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		logger.info("prepare");
		// 初始化
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		logger.info("declareOutputFields");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		logger.info("getComponentConfiguration");
		return null;
	}

}
