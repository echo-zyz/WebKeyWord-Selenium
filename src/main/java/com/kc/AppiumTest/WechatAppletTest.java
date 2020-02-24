package com.kc.AppiumTest;

import com.kc.appKeyWord.AppKeyWord;

public class WechatAppletTest {

	public static void main(String[] args) {
		// 实例化关键字类
		AppKeyWord wechat=new AppKeyWord();
		//通过cmd启动命令行版本的appium
		wechat.StartAppium("12345", "10");
		//启动微信
		wechat.runAPP("5.1.1", "127.0.0.1:62001", "com.tencent.mm", "com.tencent.mm.ui.LauncherUI", "http://127.0.0.1:12345/wd/hub", "10");
		wechat.halt("15");
//		wechat.click("//*[@text='登录']");
//		wechat.halt("4");
//		wechat.click("//*[@text='用微信号/QQ号/邮箱登录']");
//		wechat.halt("3");
//		wechat.input("//*[@text='请填写微信号/QQ号/邮箱']", "493187431");
//		wechat.input("//*[@resource-id='com.tencent.mm:id/m6']", "ACHAO19870426");
//		wechat.click("//*[@text='登录']");
		
		OpenWechatAPPlet(wechat);
		

	}

	private static void OpenWechatAPPlet(AppKeyWord wechat) {
		//向下滑动调出小程序 注意这里要等足够时间让app启动完毕才开始滑动
		wechat.appiumSwipe("400", "700", "400", "1400");
		wechat.halt("3");
		//这里滑动会触发长按 所以取消长安的窗口
		wechat.adbTap("414", "1359");
		wechat.click("//*[@text='智行火车票']");
		wechat.halt("10");
		wechat.click("//*[@text='高铁动车']");
		wechat.click("//*[@text='火车票查询']");
	}

}
