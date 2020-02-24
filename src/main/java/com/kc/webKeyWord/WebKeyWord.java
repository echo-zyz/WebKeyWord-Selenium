package com.kc.webKeyWord;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.io.Files;
import com.kc.autoLoger.AutoLogger;
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
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(browserType=="chrome") {
			GoogleDriver gg=new GoogleDriver("webDrivers/chromedriver.exe");
			driver=gg.getdriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else {
			//用selenium自己的方式来启动浏览器
			System.setProperty("webdriver.gecko.driver", "webDrivers/geckodriver.exe");
			driver=new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}
	/**
	 * 用get方法访问网站
	 * @param url
	 */
	public void visitWeb(String url) {
		try {
			driver.get(url);
			AutoLogger.log.info("访问的网址是："+url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//log4j打印报错和报错路径
			AutoLogger.log.error(e,e.fillInStackTrace());
			//e.printStackTrace();
		}
	}
	/**
	 * 基于name元素定位 然后输入内容 最后提交
	 * @param nameAttr 元素定位name
	 * @param inputContent 输入内容
	 */
	public void inputAndSubmitByname(String nameAttr,String inputContent) {
		try {
			AutoLogger.log.info("根据元素名字来输入内容并且提交");
			WebElement element = driver.findElement(By.name(nameAttr));
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}			
			// Enter something to search for
			element.sendKeys(inputContent);
			// Now submit the form. WebDriver will find the form for us from the element
			element.submit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("根据元素名字来输入内容并且提交");
			AutoLogger.log.error(e, e.fillInStackTrace());
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
			//e.printStackTrace();
			AutoLogger.log.error(e, e.fillInStackTrace());
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
			ewait.until(
					new ExpectedCondition<Boolean>() {
			         public Boolean apply(WebDriver d) {
			    	//返回一个boolean类型数据，在ExpectedCondition<Boolean> 
			         return d.getTitle().toLowerCase().startsWith("cheese");
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
			//线程休眠，让程序停止一段时间，这个时间是固定的没有任何条件来解除等待 我们等待秒单位
			Thread.sleep(t*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 关闭浏览器 关闭进程
	 */
	public void closeBrowser() {
		AutoLogger.log.info("关闭浏览器");
		driver.quit();
	}
	/**
	 * 通过xpath定位元素并且点击
	 * @param xpathExp
	 */
	public void click(String xpathExp) {
		try {
			driver.findElement(By.xpath(xpathExp)).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("点击xpath元素找不到");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 通过xpath定位输入框 然后清理输入框 输入内容
	 * @param xpathExp
	 * @param content
	 */
	public void input(String xpathExp,String content) {
		try {
			WebElement el=driver.findElement(By.xpath(xpathExp));
			el.clear();
			el.sendKeys(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			saveScrShot("inpute输入content");
			AutoLogger.log.info("input输入报错,xpath定位元素找不到。");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 上传文件 就是用input方法 
	 * @param xpathExp 元素定位
	 * @param filePath 上传文件路径
	 */
	public void uploadFile(String xpathExp,String filePath) {
		try {
			input(xpathExp, filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("上传截图失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 通过findElements找到所有符合xpath条件的元素，然后输出文本内容
	 * @param xpathExp
	 */
	public void getAllGoods(String xpathExp) {
		List<WebElement> goods=driver.findElements(By.xpath(xpathExp));
		//对每个list元素遍历 然后输出文本内容
		for(WebElement g:goods) {
			System.out.println(g.getText());
		}
	}
	/**
	 * 到iframe窗口  
	 * @param iframe iframeid来定位 或者iframe name
	 */
	public void switchToIframeByIdOrName(String iframe) {
		try {
			driver.switchTo().frame(iframe);
			System.out.println("切换iframe成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("切换iframe失败");
		}
	}
	/**
	 * 基于iframe元素 的xpath定位切换到iframe
	 * @param xpathExp
	 */
	public void switchIframe(String xpathExp) {
		try {
			WebElement frameElement=driver.findElement(By.xpath(xpathExp));
			driver.switchTo().frame(frameElement);
			System.out.println("切换iframe成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("切换iframe失败");
		}
	}
	/**
	 * iframe返回主窗口
	 */
	public void switchIframeToRoot() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AutoLogger.log.info("从iframe切回主页面失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 在selenium进行操作时，页面上的元素如果需要滚动操作条，只要selenium能够完成元素定位，可以自动滚动滚动条。但是也有特殊情况，需要自己操作。
	 *自己操作:使用js语法，在selenium中对浏览器执行js语句从而完成操作。
	 *	滚动滚动条的操作：
	 *	window.scrollTo(横坐标，纵坐标) 基于坐标滚动。
	 *	window.scrollTo(0,document.body.scrollHeight) 滚到最底端
	 */
	public void scrollToEnd() {
		JavascriptExecutor js=(JavascriptExecutor) driver;
		//直接滚动到最底部
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void scrollVertical(String yAxis) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		//通过js脚本拼接 实现滚动到置顶的y坐标位置
		js.executeScript("window.scrollTo(0, "+yAxis+")");
	}
	/**
	 * 带参数的js脚本调用，适用于通过xpath定位到元素然后条用js方法操作
	 * @cmd js脚本使用arguments[0].click()进行参数调用
	 * @xpath 通过xpath定位
	 * $x("xxx")[0].click()没有办法通过普通的js脚本实现xpath的定位。曲线救国：
	 *	首先通过xpath定位到元素，然后把元素作为Object可变参数传给jsExecutor，之后通过 js脚本 "argument[0].click()"表示调用参数列表中的第一个元素来完成点击操作。
	 *尽量少使用js注入执行的方式来进行自动化测试，因为有点作弊嫌疑。尽量selenium模拟用户操作方式。
	 */
	public void runJsWithArg(String cmd,String xpathExp) {
		//先通过xpath定位到一个元素
		WebElement ele=driver.findElement(By.xpath(xpathExp));
		//然后再js执行器执行时，把元素作为参数传进去，然后在js脚本通过argument[0]来调用
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript(cmd, ele);
	}
	public String getJsReturnWithArg(String cmd,String xpathExp) {
		WebElement ele=driver.findElement(By.xpath(xpathExp));
		JavascriptExecutor js=(JavascriptExecutor)driver;
		//在执行js脚本时候 加上return 将结果返回
		String result=js.executeScript("return "+cmd, ele).toString();
		return result;
	}
	/**
	 * 封装电商登录流程(前端)
	 */
	public void loginShop() {
		try {
			AutoLogger.log.info("开始登录电商前端");
			visitWeb("http://www.testingedu.com.cn:8000");
			click("//a[contains(string(),'登录')]");
			input("//input[@name='username']", "493187431@qq.com");
			input("//input[@name='password']", "123456");
			input("//input[@name='verify_code']", "1");
			click("//a[@name='sbtbutton']");
			halt("5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("电商前端登录失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 封装电商登录(后端)
	 */
	public void loginShopBackstage() {
		try {
			AutoLogger.log.info("开始登录电商后端");
			input("//input[@name='username']", "admin");
			input("//input[@name='password']", "123456");
			input("//input[@name='vertify']", "123456");
			click("//input[@name='submit']");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("电商后端登录失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 鼠标悬停到元素上封装。这个里面也有鼠标右键等其他方法 记得加上.perform()表示悬停操作结束
	 * @param xpathExp
	 */
	public void hover(String xpathExp) {
		try {
			Actions act=new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath(xpathExp))).perform();;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 通过浏览器标题切换浏览器窗口 切换窗口 切换句柄
	 * @param expTitle
	 */
	public void switchWindowByTitle(String expTitle) {
		try {
			Set<String> windowHands= driver.getWindowHandles();
			String targetHands="";
			//历遍所有句柄判定对应浏览器窗口标题是否符合预期
			for(String s:windowHands) {
				if(driver.switchTo().window(s).getTitle().equals(expTitle)) {
					//如果符合预期，找到了要切换的句柄
					targetHands=s;
					break;
				}
			}
			driver.switchTo().window(targetHands);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("通过标题切换浏览器失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	   * 通过select-option里面的文本内容选择元素
	 * @param xpathExp
	 * @param visiableText
	 */
	public void selectByText(String xpathExp,String visiableText) {
		try {
			Select se=new Select(driver.findElement(By.xpath(xpathExp)));
			se.selectByVisibleText(visiableText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("通过select-option里面的文本内容选择元素失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 通过select-option里面的value值来选择
	 * @param xpathExp
	 * @param value
	 */
	public void selectByValue(String xpathExp,String value) {
		try {
			Select se=new Select(driver.findElement(By.xpath(xpathExp)));
			se.selectByValue(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("通过select-option里面的value值选择元素失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 报错时后截图 一般放到catch里面,也可以自动调用
	 * @param method 这个参数描述报错的地方的动作
	 */
	public void saveScrShot(String method) {
		//获取当前时间
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
		String createDate=sdf.format(date);
		//拼接文件名形式为：工作目录路径+method+createdate+"png"
		String scrName="log/scrShot/"+method+createDate+".png";
		//以当前文件名创建文件
		File scrShot=new File(scrName);
		//将截图保存到临时文件
		File tmp= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(tmp, scrShot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * 断言元素文本内容，来判定测试用例是否执行成功
     * @param xpathExp
     * @param expText
     */
	public void assertEleText(String xpathExp,String expText) {
		try {
			String eleText=driver.findElement(By.xpath(xpathExp)).getText();
			System.out.println("断言元素文本内容："+eleText);
			if(eleText.equals(expText)) {
				System.out.println("测试成功");
			}
			else {
				System.out.println("测试失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void assertContainsEleText(String xpathExp,String expText) {
		try {
			String eleText=driver.findElement(By.xpath(xpathExp)).getText();
			System.out.println("断言元素文本内容："+eleText);
			if(eleText.contains(expText)) {
				System.out.println("测试成功");
			}
			else {
				System.out.println("测试失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 断言 页面中是否包含指定的内容
	 * @param expText
	 */
	public void assertPageContains(String expText) {
		String pageSource=driver.getPageSource();
		System.out.println(pageSource);
		if(pageSource.contains(expText)) {
			AutoLogger.log.info("测试断言成功！");
		}
		else {
			AutoLogger.log.info("测试断言失败");
		}
	}
	/**
	 * 断言某个元素=预期值
	 * @param xpathExp
	 * @param exAttrKey
	 * @param exAttrValue
	 */
	public void assertEleAttr(String xpathExp,String exAttrKey,String exAttrValue) {
		WebElement ele=driver.findElement(By.xpath(xpathExp));
		if(ele.getAttribute(exAttrKey).equals(exAttrValue)) {
			AutoLogger.log.info("测试断言成功");
		}
		else {
			AutoLogger.log.info("测试断言失败");
			}
	}
}

