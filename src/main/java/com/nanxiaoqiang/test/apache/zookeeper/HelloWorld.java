package com.nanxiaoqiang.test.apache.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @ClassName: HelloWorld
 * @Description: Zookeeper学习，连接后增加遍历删除节点
 * @author nanxiaoqiang nanxiaoqiang_gmail_com
 * @date 2015年8月15日 下午5:00:24
 *
 */
public class HelloWorld {
	private static final int CLIENT_PORT = 2181;
	public static ZooKeeper zk;
	public static String rootPath = "/testRootPath";
	public static String childPath = "/childPath";

	public static void main(String[] args) {
		try {
			zk = new ZooKeeper("localhost:" + CLIENT_PORT, 30, new Watcher() {
				// 监控所有被触发的事件
				public void process(WatchedEvent event) {
					System.out.println("已经触发了" + event.getType() + "事件！");
				}
			});
			Stat status1 = zk.exists(rootPath, false);
			if (status1 == null) {
				// 创建一个目录节点
				String returnStr = zk.create(rootPath,
						"testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
				System.out.println("created and return str:" + returnStr);
			}
			System.out.println("根节点状态:" + status1);
			// 输出节点内容
			System.out.println(new String(zk.getData(rootPath, false, null)));
			Stat status2 = zk.exists(rootPath + childPath + "1", false);
			System.out.println("子节点状态:" + status2);// 这个时候应当是null
			if (status2 == null) {
				// 创建一个目录节点
				String returnStr1 = zk.create(rootPath + childPath + "1",
						"child1path".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
				System.out.println("created " + rootPath + childPath
						+ "1 and return str:" + returnStr1);
				// 再创建一个目录节点
				String returnStr2 = zk.create(rootPath + childPath + "2",
						"child1path".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
				System.out.println("created " + rootPath + childPath
						+ "2 and return str:" + returnStr2);
			}
			// 取出子目录节点列表
			System.out.println(rootPath + " 下的子节点有："
					+ zk.getChildren("/testRootPath", true));
			// 删除子目录节点
			zk.delete(rootPath + childPath + "1", -1);
			zk.delete(rootPath + childPath + "2", -1);
			System.out.println("删除节点:" + rootPath + childPath + "1   |   "
					+ rootPath + childPath + "2");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (zk != null)
				try {
					zk.close();
					System.out.println("关闭Zookeeper");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}

	}
}
