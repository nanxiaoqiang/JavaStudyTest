package com.nanxiaoqiang.test.storm.helloworld;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.nanxiaoqiang.test.storm.helloworld.bolts.WordCounter;
import com.nanxiaoqiang.test.storm.helloworld.bolts.WordNormalizer;
import com.nanxiaoqiang.test.storm.helloworld.spouts.WordReader;

public class App {

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// 定义拓扑
		// 它决定Storm如何安排各节点，以及它们交换数据的方式。
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
				.shuffleGrouping("word-reader");
		// builder.setBolt("word-counter", new WordCounter()).shuffleGrouping(
		// "word-normalizer");

		// 创建两个节点
		// builder.setBolt("word-counter", new WordCounter()).shuffleGrouping(
		// "word-normalizer");

		// 上边创建的两个节点会发生各自统计各自的。
		// 所以要改成下边的
		builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping(
				"word-normalizer", new Fields("word"));

		// 创建一个包含拓扑配置的Config对象，它会在运行时与集群配置合并，并通过prepare方法发送给所有节点。
		Config conf = new Config();
		conf.put("wordsFile", "C:/words.txt");// "文件在../src/main/resources/storm/test/helloworld/words.txt");
		conf.setDebug(true);
		// 用一个LocalCluster对象运行这个拓扑。
		// 在生产环境中，拓扑会持续运行，不过对于这个例子而言，你只要运行它几秒钟就能看到结果。
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Topologie", conf,
				builder.createTopology());
		Thread.sleep(2000);
		cluster.shutdown();
	}
}
