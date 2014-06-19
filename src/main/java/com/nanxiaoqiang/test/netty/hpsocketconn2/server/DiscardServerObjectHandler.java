package com.nanxiaoqiang.test.netty.hpsocketconn2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.CharBuffer;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanxiaoqiang.test.netty.hpsocketconn2.entity.AlarmEntity;
import com.nanxiaoqiang.test.netty.hpsocketconn2.entity.IscsAlarmHistory;

@Sharable
public class DiscardServerObjectHandler extends
		SimpleChannelInboundHandler<Object> {

	private static Logger logger = LogManager
			.getLogger(DiscardServerObjectHandler.class.getName());

	@SuppressWarnings("unused")
	private ChannelHandlerContext ctx;

	public DiscardServerObjectHandler() {
		logger.info("DiscardServerObjectHandler");
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf b = (ByteBuf) msg;
		logger.info("messageReceived: " + b.toString(CharsetUtil.UTF_8));

		AlarmEntity ae = new AlarmEntity();
		ae.setCmd("insert");
		for (int i = 0; i < 10; i++) {
			IscsAlarmHistory iah = new IscsAlarmHistory();
			iah.setId(1L);
			iah.setAlarmtime(DateTime.now().minusMinutes(2).toDate());
			iah.setApplytime(DateTime.now().toDate());
			iah.setEqpid(11111111L);
			iah.setEqpname("风机");
			iah.setInfoid(7010101L);
			iah.setIsapply(1);
			iah.setIsnormal(1);
			iah.setLineid(7L);
			iah.setAlarmlevel("3");
			iah.setLinename("七号线");
			iah.setNormaltime(DateTime.now().minusMillis(1).toDate());
			iah.setRemark("风机故障");
			iah.setStationid(721L);
			iah.setStationname("焦化厂站");
			iah.setSystem("BAS");
			ae.getAlarms().add(iah);
		}
		ObjectMapper mapper = new ObjectMapper();
		String str = "";
		str = mapper.writeValueAsString(ae);

		if (StringUtils.isNotBlank(str)) {
			ByteBuf bf = ByteBufUtil.encodeString(ctx.alloc(), CharBuffer
					.wrap(String.format("%08d", str.getBytes().length) + str),
					CharsetUtil.UTF_8);
			logger.debug(String.format("%08d", str.getBytes().length) + str);
			ctx.writeAndFlush(bf);
		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 自己的实现，当有连接连接的时候。
		this.ctx = ctx;
		// 输出当前连接channel的id
		logger.info(ctx.channel().id().asLongText() + "|"
				+ ctx.channel().id().asShortText());
		// sendActiveMsg();
	}
}
