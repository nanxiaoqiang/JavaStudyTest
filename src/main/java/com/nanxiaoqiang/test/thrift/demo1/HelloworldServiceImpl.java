package com.nanxiaoqiang.test.thrift.demo1;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

import com.nanxiaoqiang.test.thrift.demo1.HelloworldService.Iface;

/**
 * Handler 数据处理接口
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2015年3月21日
 *
 */
public class HelloworldServiceImpl implements Iface {

	private static Logger logger = LogManager
			.getLogger(HelloworldServiceImpl.class.getName());

	ConcurrentHashMap<Integer, Helloworld> map = new ConcurrentHashMap<>();

	public HelloworldServiceImpl() {
		Helloworld h = new Helloworld();
		h.setId(0);
		h.setName("NULL");
		map.put(h.getId(), h);
		logger.info("constructor HelloworldServiceImpl:add new Helloworld,now map size is "
				+ map.size());
	}

	@Override
	public boolean insertHelloworld(Helloworld helloworld) throws TException {
		if (helloworld == null || helloworld.getId() == 0) {
			logger.info("error object of Helloworld. map size is " + map.size());
			return false;
		} else {
			logger.info(ToStringBuilder.reflectionToString(helloworld,
					ToStringStyle.MULTI_LINE_STYLE));
			map.put(helloworld.getId(), helloworld);
			logger.info("insert complete. map size is " + map.size());
			return true;
		}
	}

	@Override
	public boolean removeHelloworld(int id) throws TException {
		if (id == 0) {
			logger.info("can not remove with id 0. map size is " + map.size());
			return false;
		} else if (map.containsKey(id)) {
			logger.info(ToStringBuilder.reflectionToString(map.get(id),
					ToStringStyle.MULTI_LINE_STYLE));
			map.remove(id);
			logger.info("removed complete with id " + id + ". map size is "
					+ map.size());
			return true;
		} else {
			logger.info("can not find object with id " + id + ". map size is "
					+ map.size());
			return false;
		}
	}

	@Override
	public Helloworld getHelloworld(int id) throws TException {
		if (map.containsKey(id)) {
			Helloworld h = map.get(id);
			logger.info(ToStringBuilder.reflectionToString(h,
					ToStringStyle.MULTI_LINE_STYLE));
			return h;
		} else {
			logger.info("can not find object with id " + id + ". map size is "
					+ map.size());
			return null;
		}
	}

}
