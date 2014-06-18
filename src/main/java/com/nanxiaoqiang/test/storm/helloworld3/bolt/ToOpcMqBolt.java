package com.nanxiaoqiang.test.storm.helloworld3.bolt;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.nanxiaoqiang.test.storm.helloworld3.entity.StormMqObject;

/**
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 */
public class ToOpcMqBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(ToOpcMqBolt.class
			.getName());

	public ToOpcMqBolt() {
		logger.debug("ToOpcMqBolt");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.task.IBolt#prepare(java.util.Map,
	 * backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// 第一次运行的准备

		// initMq

	}

	@Override
	public void execute(Tuple input) {
		logger.debug("execute");
		// 得到数据，解析，并且发送给MQ

		/************* 例子START ***************/
		StormMqObject smo = (StormMqObject) input.getValue(1);
		logger.info(smo.getCorrelationId() + "|" + smo.getValues().size() + "|"
				+ smo.getTime());
		/************* 例子 END ***************/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * backtype.storm.topology.IComponent#declareOutputFields(backtype.storm
	 * .topology.OutputFieldsDeclarer)
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		logger.debug("declareOutputFields: do nothing!");
		// This bolt does not emmit any tuple
	}

}
