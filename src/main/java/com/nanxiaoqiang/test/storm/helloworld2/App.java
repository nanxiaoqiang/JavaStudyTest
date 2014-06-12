package com.nanxiaoqiang.test.storm.helloworld2;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.google.common.collect.Lists;
import com.nanxiaoqiang.test.storm.helloworld2.bolt.GetAlarm;
import com.nanxiaoqiang.test.storm.helloworld2.bolt.GetUpdate;
import com.nanxiaoqiang.test.storm.helloworld2.mem.MemObject;
import com.nanxiaoqiang.test.storm.helloworld2.spouts.GetStringArray;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// 初始化
		MemObject.getInstance();
		// TODO Auto-generated method stub
		// 定义拓扑
		// 它决定Storm如何安排各节点，以及它们交换数据的方式。
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("iscs-string-arr-reader", new GetStringArray());
		// builder.setBolt("iscs-get-update-worker", new GetUpdate())
		// .shuffleGrouping("iscs-string-arr-reader");
		builder.setBolt("iscs-get-update-worker", new GetUpdate(), 4)
				.fieldsGrouping("iscs-string-arr-reader",
						new Fields("scanIscsValue"));
		builder.setBolt("iscs-get-alarm-worker", new GetAlarm(), 4)
				.fieldsGrouping("iscs-get-update-worker",
						new Fields("updateIscsValue"));

		// 创建一个包含拓扑配置的Config对象，它会在运行时与集群配置合并，并通过prepare方法发送给所有节点。
		Config conf = new Config();
		List<String> lists = Lists.newArrayList();

		// String[] strarr = new String[2500];
		for (int i = 0; i < 1000; i++) {
			lists.add((i + 1) + "," + RandomUtils.nextInt(0, 2));
		}
		lists.add(System.currentTimeMillis() + "");
		conf.put("strArr", lists);// "文件在../src/main/resources/storm/test/helloworld/words.txt");
		conf.setDebug(false);

		// 用一个LocalCluster对象运行这个拓扑。
		// 在生产环境中，拓扑会持续运行，不过对于这个例子而言，你只要运行它几秒钟就能看到结果。
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Topologie", conf,
				builder.createTopology());
		Thread.sleep(60000);
		cluster.shutdown();
	}
}
