package com.nanxiaoqiang.test.storm.helloworld2.bolt;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsDicValue;
import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsValue;
import com.nanxiaoqiang.test.storm.helloworld2.mem.MemObject;

/**
 * 得到更新量
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月12日
 * 
 */
public class GetUpdate implements IRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(GetUpdate.class
			.getName());

	private OutputCollector collector;

	public GetUpdate() {
		// TODO Auto-generated constructor stub
		logger.debug("GetUpdate");
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		logger.debug("cleanup");
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		logger.debug("execute");
		IscsValue iv = (IscsValue) input.getValue(0);

		// 得到数据库中的数据Map
		if (MemObject.getInstance().getMap().containsKey(iv.getId())) {
			IscsDicValue idv = MemObject.getInstance().getMap().get(iv.getId());
			if (idv.getUpdateTime() < iv.getTime()) {
				if (!StringUtils.equals(idv.getValue(), (iv.getValue()))) {
					idv.setValue(iv.getValue());
					idv.setUpdateTime(iv.getTime());
					// 更新内存：
					MemObject.getInstance().getMap().put(idv.getId(), idv);
					// 发送更新量
					// collector.emit(new Values(new IscsValue(iv)));
					// 日志输出一下：
					logger.info("有更新：" + iv);
				}
			}
		}

	}

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map conf,
			TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		logger.debug("prepare");
		this.collector = collector;
		// 第一次准备
		// 类似于初始化
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		logger.debug("declareOutputFields");
		declarer.declare(new Fields("updateIscsValue"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		logger.debug("getComponentConfiguration");
		return null;
	}

}
