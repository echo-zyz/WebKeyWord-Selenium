package com.kc.AppiumTest;



import java.util.Set;

import com.kc.appKeyWord.AppKeyWord;
import com.kc.autoLoger.AutoLogger;


public class qqzone {
	public static void main(String[] args) {
		AppKeyWord qq=new AppKeyWord();
		qq.StartAppium("12345", "10");
		//打开、登录qq，因为后面登录要用到qq
	    qq.runAPP("5.1.1", "127.0.0.1:62001", "com.tencent.mobileqq", ".activity.LoginActivity", "http://127.0.0.1:12345/wd/hub", "10");
	    qq.LoginQQ();
	    //打开系统浏览器
	    qq.runBrowser("5.1.1", "127.0.0.1:62001", "http://127.0.0.1:12345/wd/hub", "10", "C:\\Users\\admin\\Desktop\\yeshenmoniqichromewebdriver\\chromedriver.exe");
	    LoginH5QZone(qq);
	}

	private static void LoginH5QZone(AppKeyWord qq) {
		qq.driver.get("https://qzone.qq.com/");
	    String nowcontext=qq.driver.getContext();
		Set<String> contexts=qq.driver.getContextHandles();
		AutoLogger.log.info("当前的context是："+nowcontext+"所有的context是："+contexts);
        //如果要使用appium inspector的xml结构树来进行元素定位，切换到NATIVE_APP
		//appium录制的脚本如果要使用，执行下这个切换
		qq.switchcontext("NATIVE_APP");
	    qq.halt("5");
	    qq.click("//*[@text='一键登录']");
	    qq.halt("3");
	    qq.click("//*[@text='登录']");
	    qq.halt("10");
	    qq.adbTap("847", "1557");
	    qq.click("//*[@text='赞']");
	}


	}


