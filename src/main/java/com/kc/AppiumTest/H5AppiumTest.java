package com.kc.AppiumTest;

import java.util.Set;

import org.openqa.selenium.By;

import com.kc.appKeyWord.AppKeyWord;
import com.kc.autoLoger.AutoLogger;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class H5AppiumTest {

	public static void main(String[] args) {
		AppKeyWord h5=new AppKeyWord();
		//通过cmd启动appium命令行版本
		h5.StartAppium("12345", "20");
		//启动手机/模拟器上的系统浏览器
		h5.runBrowser("5.1.1", "127.0.0.1:62001", "http://127.0.0.1:12345/wd/hub", "10", "C:\\Users\\admin\\Desktop\\yeshenmoniqichromewebdriver\\chromedriver.exe");
		//定位的时候默认按照web自动化方案定位，将浏览器内容看做一个html元素来进行定位
		//访问H5的网页
		h5.driver.get("https://www.youku.com/");
		//h5.runCMD("adb shell input tap 452 552");
		h5.halt("3");
		//点击登录按钮
		h5.driver.findElement(By.xpath("//img[@id=\"default-user-img\"]")).click();
		String nowcontext=h5.driver.getContext();
		Set<String> contexts=h5.driver.getContextHandles();
		AutoLogger.log.info("当前的context是："+nowcontext+"所有的context是："+contexts);
        //如果要使用appium inspector的xml结构树来进行元素定位，切换到NATIVE_APP
		//appium录制的脚本如果要使用，执行下这个切换
		h5.driver.context("NATIVE_APP");//现在开始使用nativeapp元素定位(appium元素定位)
		h5.halt("10");
		h5.click("//*[@content-desc='账号登录']");
		h5.halt("5");
		h5.click("//*[@resource-id='fm-login-id']");
		h5.input("//*[@resource-id='fm-login-id']", "18617099614");
		h5.input("//*[@resource-id='fm-login-password']", "qq08683065"); 
		h5.click("//*[@content-desc='登录']");
		
//		h5.driver.context("CHROMIUM");
//		AutoLogger.log.info("现在的context是："+h5.driver.getContext());
//		h5.switchToIframeByIdOrName("alibaba-login-box");
//		h5.driver.findElement(By.xpath("//input[@id='fm-login-id']")).sendKeys("18617099614");;;
//		h5.driver.findElement(By.xpath("//input[@id='fm-login-password']")).sendKeys("qq08683065");;
//		h5.driver.findElement(By.xpath("//button[@type='submit']")).click();
//		h5.halt("5");
		
//		h5.switchcontext("NATIVE_APP");
//		h5.halt("3");
//		//tap搜索框 
//		h5.adbTap("298", "144");
//		h5.halt("3");
//		h5.adbTap("298", "144");
//		h5.halt("3");
//		//通过cmd adb shell input text 输入内容
//		h5.runCMD("adb shell input text 地球脉动 ");
		//随意tap测试
//		h5.adbTap("181", "233");
//		h5.halt("3");
//		h5.adbTap("181", "233");
	}

}
