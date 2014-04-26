package com.liuyb.app;

import org.junit.Test;

import cn.liuyb.app.common.utils.MD5;

public class Test1 {
	
	@Test
	public void test(){
		String PASS_OBFUSCATE = "tbmis!!$%stbmis";
		String pass = "1234567";
		String password = String.format(PASS_OBFUSCATE, pass);
		System.out.println(MD5.getMD5(password, "utf-8"));
	}
	
	@Test 
	public void testV2(){  
	    String s = "res/drawable-hdpi/icon.png";
	    System.out.println(s.lastIndexOf("/"));
	    System.out.println(s.length());
	System.out.println(s.substring(s.lastIndexOf("/")+1,s.length()));
	} 
}
