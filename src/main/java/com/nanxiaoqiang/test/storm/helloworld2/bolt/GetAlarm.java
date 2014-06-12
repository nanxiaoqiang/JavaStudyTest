package com.nanxiaoqiang.test.storm.helloworld2.bolt;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsDicValue;
import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsValue;
import com.nanxiaoqiang.test.storm.helloworld2.mem.MemObject;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class GetAlarm extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(GetAlarm.class
			.getName());

	@SuppressWarnings("unused")
	private OutputCollector collector;

	public GetAlarm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
			TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		logger.debug("prepare");
		this.collector = collector;

	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		logger.debug("execute");
		IscsValue iv = (IscsValue) input.getValue(0);
		// 得到数据库中的数据Map
		if (MemObject.getInstance().getAlarmMap().containsKey(iv.getId())) {
			IscsDicValue idv = MemObject.getInstance().getAlarmMap()
					.get(iv.getId());
			if (idv.getIs_alarm() == 1) {
				if (StringUtils.equals(idv.getAlarm_value(), iv.getValue())) {
					// 当前为报警
					logger.info("有报警:Level:" + idv.getAlarm_level() + "|" + iv);
				} else {
					// 当前为消警
					logger.info("有消警:Level:" + idv.getAlarm_level() + "|" + iv);
				}
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		logger.debug("declareOutputFields");

	}

}
