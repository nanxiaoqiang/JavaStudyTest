package com.nanxiaoqiang.test.storm.helloworld.bolts;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * 负责分割单词
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月11日
 * 
 */
public class WordNormalizer implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(WordNormalizer.class
			.getName());

	private OutputCollector collector;

	public WordNormalizer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		logger.info("cleanup");
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		logger.info("execute");
		// 按空格切分句子
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for (String word : words) {
			word = word.trim();
			if (!word.isEmpty()) {
				// 转换为小写
				word = word.toLowerCase();
				// 发布这个单词
				collector.emit(new Values(word));
			}
		}
		// 对元组做出应答
		collector.ack(input);
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map conf,
			TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		logger.info("prepare");
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		logger.info("declareOutputFields");
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		logger.info("getComponentConfiguration");
		return null;
	}

}
