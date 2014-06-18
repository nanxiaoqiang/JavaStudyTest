package com.nanxiaoqiang.test.storm.server.spout;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.google.common.collect.Maps;
import com.nanxiaoqiang.test.storm.server.common.Constants;
import com.nanxiaoqiang.test.storm.server.entity.IscsValue;
import com.nanxiaoqiang.test.storm.server.entity.StormMqObject;
import com.nanxiaoqiang.test.storm.server.jms.activemq.ActiveMqClient;

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
 *                             -->其他的可能的实时数据展示。
 * 4)etc…
 * </pre>
 * 
 * @author nanxiaoqiang
 * 
 * @version ver0.1.2 2014年6月18日
 */
public class GetRegisterSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(GetRegisterSpout.class
			.getName());

	private SpoutOutputCollector collector;

	/** MQ的配置文件 **/
	/**
	 * ActiveMQ的地址
	 */
	private String mq_url;

	/**
	 * ActiveMQ的队列名称
	 */
	private String mq_queue;

	/**
	 * ActiveMQ的用户名
	 */
	private String mq_usr;

	/**
	 * ActiveMQ的密码
	 */
	private String mq_pwd;

	/**
	 * ActiveMQ客户端，用于读取
	 */
	private ActiveMqClient activeMqClient;

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

		this.collector = collector;

		// 实际情况：
		// 应当从conf得到配置文件，然后启动一个读取MQ的实例。
		mq_url = conf.get(Constants.MQ_URL).toString();
		mq_queue = conf.get(Constants.MQ_QUEUE).toString();
		mq_usr = conf.get(Constants.MQ_USR).toString();
		mq_pwd = conf.get(Constants.MQ_PWD).toString();

		// init database reader here
		// 得到数据库信息，放到内存中
		initMem();
		// init MQ here
		initMQ();

	}

	/**
	 * 初始化MQ客户端
	 */
	private void initMQ() {
		try {
			activeMqClient = new ActiveMqClient(mq_url, mq_queue, mq_usr,
					mq_pwd, "GetRegisterSpoutActiveMqReadNamedThread");
		} catch (JMSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 初始化内存数据
	 */
	private void initMem() {

	}

	@Override
	public void nextTuple() {
		logger.debug("nextTulp");

		Message message = this.activeMqClient.receive();
		if (message == null) {
			logger.debug("从MQ中没有取得数据!");
			try {
				// 休息500ms秒，或者可以休息更少的时间
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error("线程休眠1sec出错了！");
			}
		} else {
			// 从MQ中取得了数据
			if (message instanceof TextMessage) {
				// 说明是个命令
			} else if (message instanceof ObjectMessage) {
				// 说明是数据
			}
		}

		// 在这里用初始化的MQ程序去读取寄存器信息，得到String[]的数据和correlation ID
		// 下边的两个变量可以放到外边。
		String[] readData;
		String correlationId;
		// 方案2:
		// 用初始化MQ程序去读取寄存器信息，并得到update变量和correlation ID。
		// 返回一个StormMqObject对象。
		// 最好能够指定的把数据给某个下行的bolt
		Map<Long, IscsValue> updates = Maps.newConcurrentMap();
		// 以下需要
		// if(读取结果 == null || 读取结果大小 ＝＝ 0 ) {
		// try{
		// // 休息1秒
		// Thread.sleep(1000);
		// }catch(InterrupedException e){logger.error("出错了！");}
		// }else{
		StormMqObject smo = new StormMqObject();

		/************* 例子START ***************/

		Object obj = this.activeMqClient.receive();
		if (obj == null) {
			logger.debug("取出来的东西是个空的!");
			try {
				// 休息1秒
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("线程休眠1sec出错了！");
			}
		} else {
			// 此处的例子接收一个TextMessage，内容为id,value;id,value;longUtcTime
			if (obj instanceof String) {
				String[] objs = ((String) obj).split(";");
				for (int i = 0; i < objs.length - 1; i++) {
					String[] temp = objs[i].split(",");
					IscsValue iv = new IscsValue(Long.parseLong(temp[0]),
							temp[1]);
					updates.put(iv.getId(), iv);
				}
				int i = RandomUtils.nextInt(5);
				smo.setCorrelationId("test" + i);
				smo.setValues(updates);
				smo.setTime(Long.parseLong(objs[objs.length - 1]));
				collector.emit(new Values(smo.getCorrelationId(), smo));
			}
		}
		/************* 例子 END ***************/
		// collector.emit(new Values(smo.getCorrelationId(), smo));
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
		declarer.declare(new Fields("mqCorrelationId", "mqUpdateDatas"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see backtype.storm.topology.base.BaseRichSpout#close()
	 */
	@Override
	public void close() {
		super.close();
		logger.debug("close");
		// 关闭ActiveMQ的连接
		this.activeMqClient.close();
	}

}
