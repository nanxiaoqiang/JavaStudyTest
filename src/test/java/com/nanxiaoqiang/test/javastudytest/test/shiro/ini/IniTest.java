package com.nanxiaoqiang.test.javastudytest.test.shiro.ini;

import static org.junit.Assert.assertEquals;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class IniTest {

	public IniTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void initest() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro/shiro-ini.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory
				.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123");

		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		assertEquals(true, subject.isAuthenticated());
		// Assert.assertEquals(true, subject.isAuthenticated());

		subject.logout();
	}

}
