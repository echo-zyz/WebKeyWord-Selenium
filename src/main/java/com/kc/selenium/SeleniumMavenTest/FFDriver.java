package com.kc.selenium.SeleniumMavenTest;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

//Firefox浏览器驱动类
public class FFDriver {
	private WebDriver driver = null;

	public FFDriver(String propath, String driverpath) {
		// 设置 Firefox驱动的路径
		System.setProperty("webdriver.gecko.driver", driverpath);
		// 设置Firefox的安装目录,如果不需要设置，那么参数给一个空字符串
		if (propath != null && propath.length() > 0) {
			System.setProperty("webdriver.firefox.bin", propath);
		}

		// 加载火狐的用户文件
		// 用户文件地址可以通过在火狐地址栏中输入about:support找到配置文件路径，复制来即可，但是由于加载配置，启动会花费比较久的时间。
		File pro=new File("C:\\Users\\admin\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\z6cen64t.default-1581078855983");
		FirefoxProfile profile=new FirefoxProfile(pro);
		FirefoxOptions opt=new FirefoxOptions();
		opt.setProfile(profile);
		

		// 创建一个 Firefox的浏览器实例，赋值给成员变量
		try {
			driver = new FirefoxDriver(opt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("log--error：创建driver失败！！");
		}

	}

	public WebDriver getdriver() {
		return this.driver;
	}
}