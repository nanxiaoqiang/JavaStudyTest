package com.nanxiaoqiang.test.storm.helloworld3;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

import com.nanxiaoqiang.test.storm.helloworld3.bolt.ToOpcMqBolt;
import com.nanxiaoqiang.test.storm.helloworld3.spouts.GetRegisterSpout;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// 定义拓扑
		// 它决定Storm如何安排各节点，以及它们交换数据的方式。
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("started", new GetRegisterSpout());
		builder.setBolt("end", new ToOpcMqBolt()).shuffleGrouping("started");

		Config conf = new Config();
		conf.put("mq_url", "failover:(tcp://localhost:61616)");
		conf.put("mq_queue", "L7_MCS_polling.queue");
		conf.put("mq_usr", "");
		conf.put("mq_pwd", "");

		conf.setDebug(false);

		// 用一个LocalCluster对象运行这个拓扑。
		// 在生产环境中，拓扑会持续运行，不过对于这个例子而言，你只要运行它几秒钟就能看到结果。
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Topologie", conf,
				builder.createTopology());
		// Thread.sleep(6000);
		// cluster.shutdown();
	}

}
