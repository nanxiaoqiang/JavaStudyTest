package com.nanxiaoqiang.test.shiro.ini;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * 从ini文件读取token，判断是否登录，最后退出，这个最后移到了test里边
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年10月14日
 * 
 */
public class IniTest {

	private static Logger logger = LogManager
			.getLogger(IniTest.class.getName());

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

		UsernamePasswordToken token = new UsernamePasswordToken("admin1", "123");

		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
		} catch (LockedAccountException e) {
			logger.error("该用户已锁定。" + e.getMessage());
			e.printStackTrace();
		} catch (DisabledAccountException e) {
			logger.error("该用户已经禁用。" + e.getMessage());
			e.printStackTrace();
		} catch (IncorrectCredentialsException e) {
			logger.error("用户名密码错误。" + e.getMessage());
			e.printStackTrace();
		} catch (UnknownAccountException e) {
			logger.error("不存在的用户。" + e.getMessage());
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// 如果是用户名密码错误
			// org.apache.shiro.authc.IncorrectCredentialsException:
			// Submitted credentials for token
			// [org.apache.shiro.authc.UsernamePasswordToken - admin,
			// rememberMe=false]
			// did not match the expected credentials.
			// 如果是不存在的用户
			// org.apache.shiro.authc.UnknownAccountException:
			// Realm
			// [org.apache.shiro.realm.text.IniRealm@27ec6a9a] was unable to
			// find account data for the submitted AuthenticationToken
			// [org.apache.shiro.authc.UsernamePasswordToken - admin1,
			// rememberMe=false].
			e.printStackTrace();
		}

		// assertEquals(true, subject.isAuthenticated());
		// Assert.assertEquals(true, subject.isAuthenticated());

		subject.logout();
	}

}
