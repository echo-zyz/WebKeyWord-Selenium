package com.kc.webKeyWord;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kc.selenium.SeleniumMavenTest.FFDriver;
import com.kc.selenium.SeleniumMavenTest.GoogleDriver;
import com.kc.selenium.SeleniumMavenTest.IEDriver;

public class WebKeyWord {
	// 创建私有成员变量，让每一个方法都用同一个driver对象来操作
	private WebDriver driver = null;

	/**
	 * 不同的浏览器都要完成driver的实例化，所以封装浏览器启动方法
	 * @param browserType
	 */
	public void openBrowser(String browserType) {
		if(browserType=="ie") {
			IEDriver ie=new IEDriver("webDrivers/IEDriverServer.exe");
			//因为成员变量driver已经创建并且赋值为null，这里直接赋值driver	
			driver=ie.getdriver();
			//隐式等待设定最长时间为10s，在10s内如果进行driver.findelement操作，则会等待元素能够被定位，
			//如果10s内被定位进行下一步操作，如果超时会报错
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(browserType=="firefox") {
			FFDriver ff=new FFDriver("", "webDrivers/geckodriver.exe");
	    	driver = ff.getdriver();
		}
		else if(browserType=="chrome") {
			GoogleDriver gg=new GoogleDriver("webDrivers/chromedriver.exe");
			driver=gg.getdriver();
		}
		else {
			//用selenium自己的方式来启动浏览器
			System.setProperty("webdriver.gecko.driver", "webDrivers/geckodriver.exe");
			driver=new FirefoxDriver();
		}
	}
	/**
	 * 用get方法访问网站
	 * @param url
	 */
	public void visitWeb(String url) {
		try {
			driver.get(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 基于name元素定位 然后输入内容 最后提交
	 * @param nameAttr 元素定位name
	 * @param inputContent 输入内容
	 */
	public void inputAndSubmitByname(String nameAttr,String inputContent) {
		try {
			WebElement element = driver.findElement(By.name(nameAttr));
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Enter something to search for
			element.sendKeys(inputContent);

			// Now submit the form. WebDriver will find the form for us from the element
			element.submit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 *获取网页标题并且返回
	 * @return
	 */
	public String getTitle() {
		try {
			String getTitle=driver.getTitle();
			return getTitle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "获取标题失败！";
	}
	/**
	 * 显式等待指定一个最长的等待时间，在这个时间内反复缺人预期事件是否发生了，如果发生等待结束，继续执行
	 * 如果超时还未发生就会报错
	 */
	public void explicitlyWaitTitle() {
		try {
			//显示等待 设置一个预期超时时间
			WebDriverWait ewait=new WebDriverWait(driver, 10);
			//匿名内部类声明
			//ExpectedCondition就是预期实践
			ewait.until(new ExpectedCondition<Boolean>() {
			    public Boolean apply(WebDriver d) {
			    	//返回一个boolean类型数据，在ExpectedCondition<Boolean>
			        return d.getTitle().toLowerCase().startsWith(getTitle());
			    }
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 等待某个元素定位表达式能够在页面中定位到一个元素
	 * @param xpathEle
	 */
	public void explicitlyWaitEle(String xpathEle) {
		try {
			WebDriverWait ewait=new WebDriverWait(driver, 10);
			ewait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath(xpathEle));
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 显式等待还可以使用selenium已经预定义好的一些等待条件。
	 * @param xpathEle
	 */
	public void explicitlyWaitEleloc(String xpathEle) {
		try {
			WebDriverWait ewait=new WebDriverWait(driver, 10);
			ewait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathEle)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 强制等待 线程休眠最死板的等待没有结束等待条件 固定等待时间 通常用于一些不确定原因的等待
	 * @param waitTime
	 */
	public void halt(String waitTime) {
		int t=Integer.parseInt(waitTime);
		try {
			//线程休眠，让程序停止一段时间，这个时间是固定的没有任何条件来解除等待
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 关闭浏览器 关闭进程
	 */
	public void closeBrowser() {
		driver.quit();
	}
}

