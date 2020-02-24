package com.kc.AppiumTest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.kc.appKeyWord.AppDriver;
import com.kc.appKeyWord.AppKeyWord;
import com.kc.autoLoger.AutoLogger;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class QQappTest {
	public static void main(String[] args){
		//关键字实例化
		AppKeyWord app=new AppKeyWord();
		//启动appim命令行版本
		app.StartAppium("12345","10");
		//设置appium 跑app的参数并且启动app
		app.runAPP("6.0.1", "127.0.0.1:7555", "com.tencent.mobileqq", ".activity.LoginActivity", "http://127.0.0.1:12345/wd/hub", "10");
		AutoLogger.log.info("=====================测试开始=================");
		//登录qq
        QQLogin(app);
        app.halt("5");
        //断言登录是否成功
        app.assertSame("//*[@resource-id='com.tencent.mobileqq:id/ivTitleName']", "消息");
        //给qq空间roy老师第一条说说点赞和留言
        QzoneLikesAndComments(app);
        app.halt("5");
        //断言留言是否成功
        app.assertByTagName("//*[@content-desc='roy老师好帅！']", "roy老师好帅！");
        
        app.runCMD("taskkill /F /IM cmd.exe");
        
	 }

	private static void QzoneLikesAndComments(AppKeyWord app) {
        app.halt("5");
        app.click("//*[@resource-id='com.tencent.mobileqq:id/et_search_keyword']");
        app.input("//*[@resource-id='com.tencent.mobileqq:id/et_search_keyword']","roy");
        app.halt("5");
        //key.click("//*[@class='android.widget.RelativeLayout']");
        app.adbTap("459", "214");
        app.halt("5");
        app.click("//*[@content-desc='聊天设置']");
        app.click("//*[@resource-id='com.tencent.mobileqq:id/clw']");
        app.click("//*[@content-desc='QQ空间']");
        app.halt("5");
        
        app.adbTap("522", "780");
        app.halt("5");
        app.click("//*[@resource-id='com.tencent.mobileqq:id/c7p']");
        
        app.click("//*[@resource-id='com.tencent.mobileqq:id/bh5']"); 
        app.input("//*[@resource-id='com.tencent.mobileqq:id/h_0']", "roy老师好帅！");
        app.click("//*[@resource-id='com.tencent.mobileqq:id/h8u']");
	}

	private static void QQLogin(AppKeyWord app) {
		app.input("//*[@content-desc='请输入QQ号码或手机或邮箱']", "493187431");
        app.input("//*[@content-desc='密码 安全']", "ACHAO19870426");
        app.click("//*[@content-desc='登 录']");
	}
}
	  
	
//	private static DesiredCapabilities SetParameters() {
//		DesiredCapabilities cap=new DesiredCapabilities();
//		//必要参数
//		cap.setCapability("deviceName", "127.0.0.1:7555");
//		cap.setCapability("platformVersion", "6.0.1");
//		cap.setCapability("appPackage", "com.tencent.mobileqq");
//		cap.setCapability("appActivity", ".activity.LoginActivity");
//		cap.setCapability("platformName", "Android");
//		//可选参数
//		//不重新签名
//		cap.setCapability("noSign", true);
//		//不清除app数据
//		cap.setCapability("noReset", true);
//		//当需要输入中文的时候，把这2个cap一起加上
//		cap.setCapability("unicodeKeyboard", true);
//		cap.setCapability("resetKeyboard", true);
//		//电脑连接了多个设备需要制定设备
//		cap.setCapability("udid", "127.0.0.1:7555");
//		return cap;
//	}
//	
//	private static void StartQQ(AndroidDriver<WebElement> driver) {
//		MobileElement el2 = (MobileElement) driver.findElementByAccessibilityId("请输入QQ号码或手机或邮箱");
//		el2.click();
//		el2.clear();
//		el2.sendKeys("493187431");
//		MobileElement el3 = (MobileElement) driver.findElementByAccessibilityId("密码 安全");
//		el3.click();
//		el3.clear();
//		el3.sendKeys("ACHAO19870426");
//		MobileElement el4 = (MobileElement) driver.findElementByAccessibilityId("登 录");
//		el4.click();
//	}
//
//	private static AndroidDriver<WebElement> StartAppiumsession(DesiredCapabilities cap) throws MalformedURLException {
//		//需要制定服务器的ip和端口完成连接
//		AndroidDriver<WebElement> driver=new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
//		//隐式等待
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		return driver;
//	}
