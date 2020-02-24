package com.kc.AppiumTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;

public class GSHXLLoginTest {

	public static void main(String[] args) throws MalformedURLException {
		// 设置客户端参数
		DesiredCapabilities cap=new DesiredCapabilities();
		//必要参数
				cap.setCapability("deviceName", "127.0.0.1:7555");
				cap.setCapability("platformVersion", "6.0.1");
				cap.setCapability("appPackage", "com.tencent.tmgp.gshx2");
				cap.setCapability("appActivity", ".GL2JNIActivity");
				cap.setCapability("platformName", "Android");
				//可选参数
				//不重新签名
				cap.setCapability("noSign", true);
				//不清除app数据
				cap.setCapability("noReset", true);
				//当需要输入中文的时候，把这2个cap一起加上
				cap.setCapability("unicodeKeyboard", true);
				cap.setCapability("resetKeyboard", true);
				//电脑连接了多个设备需要制定设备
				cap.setCapability("udid", "127.0.0.1:7555");

				//启动会话，启动androiddriver完成手机的连接和app的启动操作
				//需要制定服务器的ip和端口完成连接
				AndroidDriver<WebElement> driver=new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
				//隐式等待
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				
				//调用adb命令
				try {
					java.lang.Process process = Runtime.getRuntime().exec("adb shell input tap 287 410");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				try {
					java.lang.Process process = Runtime.getRuntime().exec("adb shell input tap 541 551");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				//使用tap()来点击
				int xAxis= Integer.parseInt("636");
				int yAxis= Integer.parseInt("554");
			    TouchAction ta=new TouchAction(driver);
			    PointOption pressPoint=PointOption.point(xAxis,yAxis);
			    //action类分解动作 先长按 再移动到制定位置 再松开
			    ta.tap(pressPoint).release().perform();
			
	}

}
