package com.nanxiaoqiang.test.storm.helloworld2.spouts;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.nanxiaoqiang.test.storm.helloworld2.entity.IscsValue;

/**
 * 第二个HelloWorld的例子，用于接收String[]。然后解析成IscsValue对象，Id是IscsValue.id然后扔出去。
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月12日
 * 
 */
public class GetStringArray implements IRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(GetStringArray.class
			.getName());

	@SuppressWarnings("unused")
	private TopologyContext context;
	private SpoutOutputCollector collector;
	private String[] strArr;
	private List<String> strList;

	public GetStringArray() {
		// TODO Auto-generated constructor stub
		logger.debug("GetStringArray");
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		logger.debug("ack");

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		logger.debug("activate");
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		logger.debug("close");
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		logger.debug("deactivate");
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		logger.debug("fail");
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		logger.debug("nextTuple");
		// nextTuple()会在同一个循环内被ack()和fail()周期性的调用。
		// 没有任务时它必须释放对线程的控制，其它方法才有机会得以执行。
		// 因此nextTuple的第一行就要检查是否已处理完成。
		// 如果完成了，为了降低处理器负载，会在返回前休眠一毫秒。如果任务完成了，文件中的每一行都已被读出并分发了。
		// 下边这个没有用
		// if (completed) {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // 什么也不做
		// }
		// return;
		// }
		// Map<Long, String> map = Maps.newConcurrentMap();
		// Long timestamp = Long.valueOf(strArr[strArr.length - 1]);
		// for (int i = 0; i < strArr.length - 1; i++) {
		// String[] keyValue = strArr[i].split(",");
		// IscsValue iv = new IscsValue(Long.valueOf(keyValue[0]),
		// keyValue[1], timestamp);
		// this.collector.emit(new Values(iv), iv.getId());
		// }
		Long timestamp = Long.valueOf(strList.get(strList.size() - 1));
		for (int i = 0; i < strList.size() - 1; i++) {
			String[] keyValue = strList.get(i).split(",");
			IscsValue iv = new IscsValue(Long.valueOf(keyValue[0]),
					keyValue[1], timestamp);
			this.collector.emit(new Values(iv), iv.getId());
		}

	}

	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf,
			TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		logger.debug("open");
		this.context = context;
		strList = (List<String>) conf.get("strArr");
		// strArr = (String[]) conf.get("strArr");
		this.collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		logger.debug("declareOutputFields");
		declarer.declare(new Fields("scanIscsValue"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		logger.debug("getComponentConfiguration");
		return null;
	}

}
