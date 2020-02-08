package com.kc.selenium.SeleniumMavenTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IEDemo {
	public static void main(String[] args) {
//		// 设置临时系统变量，让代码能找到driver.exe，不同的浏览器配置不同的参数，并且把driver.exe放到maven里面用相对路径调用
//		System.setProperty("webdriver.ie.driver", "webDrivers/IEDriverServer.exe");
//		// Create a new instance of the Firefox driver
//		// Notice that the remainder of the code relies on the interface,
//		// not the implementation.
//		// 实例化webdriver，完成浏览器启动
//		WebDriver driver = new InternetExplorerDriver();
		IEDriver ie=new IEDriver("webDrivers/IEDriverServer.exe");
		WebDriver driver=ie.getdriver();

		// 访问浏览器
		driver.get("http://www.baidu.com");
		// Alternatively the same thing can be done like this
		// driver.navigate().to("http://www.google.com");

		// 实例化WebElement 基于输入框查找name="wd"元素
		WebElement element = driver.findElement(By.name("wd"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the element
		element.submit();

		// Check the title of the page
		// getTitle()获取title元素
		System.out.println("Page title is: " + driver.getTitle());

		// Google's search is rendered dynamically with JavaScript.
		// Wait for the page to load, timeout after 10 seconds
		// 显式等待，等待页面title变成cheese开始，如果超过10s，没有找到则报错
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("cheese!");
			}
		});

		// Should see: "cheese! - Google Search"
		System.out.println("Page title is: " + driver.getTitle());

		// Close the browser
		driver.quit();
	}
}