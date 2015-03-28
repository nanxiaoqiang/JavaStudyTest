package com.nanxiaoqiang.test.shiro.ini2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm implements Realm {

	private static Logger logger = LogManager
			.getLogger(MyRealm.class.getName());

	public MyRealm() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		// return null;
		return "myRealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// TODO Auto-generated method stub
		// return false;
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		// return null;
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		if (!"admin".equals(username)) {
			logger.warn("用户名不对");
			throw new UnknownAccountException();
		}
		if (!"123".equals(password)) {
			logger.warn("密码不对");
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
