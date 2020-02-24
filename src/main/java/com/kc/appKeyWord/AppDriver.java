package com.kc.appKeyWord;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;

/**
 * 封装启动androiddriver完成手机链接和app启动操作
 * @author admin
 *
 */
public class AppDriver {
	//启动会话，启动androiddriver完成手机的连接和app的启动操作 这里用私有的成员变量给与null，后续再给值
	private AndroidDriver driver = null;
	public AppDriver(String platformVersion,String deviceName,String appPackage,String appActivity,String appiumServerIP,String waitTime ) {
		//设置客户端参数
		DesiredCapabilities cap=new DesiredCapabilities();
		//必要参数
		cap.setCapability("deviceName", deviceName);
		cap.setCapability("platformVersion", platformVersion);
		cap.setCapability("appPackage", appPackage);
		cap.setCapability("appActivity", appActivity);
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
		cap.setCapability("udid", deviceName);
		
		//需要制定服务器的ip和端口完成连接 这里的appiumServerIP 填写appium服务器地址 记得后面加/wd/hub
		try {
			driver = new AndroidDriver(new URL(appiumServerIP), cap);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//隐式等待
		int t=Integer.parseInt(waitTime);
		driver.manage().timeouts().implicitlyWait(t*1000, TimeUnit.SECONDS);
	}
	public AndroidDriver getdriver() {
		return this.driver;
	}
}
