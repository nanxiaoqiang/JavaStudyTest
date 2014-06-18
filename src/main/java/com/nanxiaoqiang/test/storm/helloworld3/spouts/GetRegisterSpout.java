package com.nanxiaoqiang.test.storm.helloworld3.spouts;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.google.common.collect.Maps;
import com.nanxiaoqiang.test.storm.helloworld3.common.Constants;
import com.nanxiaoqiang.test.storm.helloworld3.entity.IscsValue;
import com.nanxiaoqiang.test.storm.helloworld3.entity.StormMqObject;

/**
 * 获取寄存器的初始程序，起始节点。<br/>
 * 
 * <pre>
 * 第一版：
 * 1)spout从接收MQ接收到数据。
 * 2)判断类型:
 *     如果是ObjectMessage(数据)，那么解析、判断更新量。
 *     如果是TextMessage(命令)，那么解析，根据具体情况
 * 3)流程设计：
 *     MQ-->Spout(接收解析更新量)-->发送到OPC(到MQ)
 *                             -->存储到MySQL(状态改变量的历史表)
 *                             -->客流判断(如果时间点A改变，那么B和C点的客流需要存储到数据库)-->当日的按照车站的进出站累加(存储或更新到当日汇总表)
 *                             -->报警判断(得到报警insert、update、delete)-->存储报警列表
 *                                                                     -->加减实时车站报警类型count
 *                                                                     -->add并统计单日车站报警类型count
 *                                                                     -->发送给Socket服务端做前台交互
 *                             -->
 * 4)
 * </pre>
 * 
 * @author nanxiaoqiang
 * 
 * @version 2014年6月18日
 */
public class GetRegisterSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(GetRegisterSpout.class
			.getName());

	// private TopologyContext context;

	private SpoutOutputCollector collector;

	// MQ的配置文件
	/**
	 * ActiveMQ的队列地址
	 */
	private String queue_url;

	/**
	 * ActiveMQ的用户名
	 */
	private String queue_usr;

	/**
	 * ActiveMQ的密码
	 */
	private String queue_pwd;

	// private Map<Long, IscsValue> updates = Maps.newConcurrentMap();

	public GetRegisterSpout() {
		logger.debug("GetRegisterSpout");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.spout.ISpout#open(java.util.Map,
	 * backtype.storm.task.TopologyContext,
	 * backtype.storm.spout.SpoutOutputCollector)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		logger.debug("open");
		// this.context = context;
		// 实际情况：
		// 应当从conf得到配置文件，然后启动一个读取MQ的实例。
		queue_url = conf.get(Constants.QUEUE_URL).toString();
		queue_usr = conf.get(Constants.QUEUE_USR).toString();
		queue_pwd = conf.get(Constants.QUEUE_PWD).toString();

		// 在此处根据得到的三个参数，或者还有更多的参数。得到MQ的寄存器信息（String[]）
		// 然后在nextTuple中传送给其他的节点。
		// 得到数据库信息，放到内存中

		// init MQ here

		// init database reader here

		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		logger.debug("nextTulp");
		// 在这里用初始化的MQ程序去读取寄存器信息，得到String[]的数据和correlation ID
		// 下边的两个变量可以放到外边。
		String[] readData;
		String correlationId;
		// 方案2:
		// 用初始化MQ程序去读取寄存器信息，并得到update变量和correlation ID。

		Map<Long, IscsValue> updates = Maps.newConcurrentMap();
		// 以下需要
		// if(读取结果 == null || 读取结果大小 ＝＝ 0 ) {
		// try{}catch(InterrupedException e){logger.error("出错了！")}
		// }else{
		StormMqObject smo = new StormMqObject();
		collector.emit(new Values(smo.getCorrelationId(), smo));
		// }
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
		logger.debug("declareOutputFields");
		declarer.declare(new Fields("correlationId", "updateDatas"));
	}

}
