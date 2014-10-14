package com.nanxiaoqiang.test.shiro.ini;

import static org.junit.Assert.assertEquals;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * 从ini文件读取token，判断是否登录，最后退出，这个最后移到了test里边
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年10月14日
 *
 */
public class IniTest {

	public IniTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
