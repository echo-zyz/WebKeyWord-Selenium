package com.kc.appKeyWord;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.io.Files;
import com.kc.autoLoger.AutoLogger;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class AppKeyWord {
	//声明成员变量driver，在所有的方法当中需要调用
	public AndroidDriver driver;
	public AppKeyWord() {
		
	}
	/**
	 * 通过cmd启动命令行版本的appium
	 */
	public void StartAppium(String port,String time) {
		AutoLogger.log.info("通过cmd启动appiumserver服务");
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
		// 当前时间的字符串
		String createdate=sdf.format(date);
		// 拼接文件名，形式为：工作目录路径+执行时间+AppiumLog.txt
		String appiumLogFile="log/"+createdate+"AppiumLog.txt";
		String startAppiumCMD ="appium -a 127.0.0.1 -p " + port + " -g" + appiumLogFile +" --local-timezone --log-timestamp";
		runCMD(startAppiumCMD);
		try {
			int t = 1000;
			t = Integer.parseInt(time);
			Thread.sleep(t*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行cmd命令
	 * @param str
	 */
	public void runCMD(String str) {
		//打开新的cmd窗口执行指定的dos命令，注意空格别丢了，否则拼出来的bat命令会连在一起。
		String cmd="cmd /c start "+str;
		Runtime runtime=Runtime.getRuntime();
		try {
			AutoLogger.log.info("执行cmd命令:"+str);
			runtime.exec(cmd);
		} catch (IOException e) {
			AutoLogger.log.info("执行命令失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 强制等待
	 * @param time
	 */
	public void halt(String time) {
		int t=Integer.parseInt(time);
		try {
			Thread.sleep(t*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 启动app
	 * @param platformVersion
	 * @param deviceName
	 * @param appPackage
	 * @param appActivity
	 * @param appiumServerIP
	 * @param waitTime
	 */
	public void runAPP(String platformVersion,String deviceName,String appPackage,String appActivity,String appiumServerIP,String waitTime) {
		try {
			AutoLogger.log.info("启动待测试APP");
			AppDriver app=new AppDriver(platformVersion, deviceName, appPackage, appActivity, appiumServerIP, waitTime);
			driver=app.getdriver();
			//全局隐试等待
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			AutoLogger.log.info("启动失败！");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 * 启动手机系统浏览器（chrome）
	 * @param platformVersion  手机安卓版本
	 * @param deviceName  设备名字
	 * @param appiumServerIP   appium服务器地址 记得后面加上/wd/hub
	 * @param waitTime 隐式等待时间
	 * @param driverPath chromedriver的存放路径
	 */
	public void runBrowser(String platformVersion,String deviceName,String appiumServerIP,String waitTime,String driverPath ) {
		try {
			AutoLogger.log.info("启动手机系统浏览器（chrome）");
			BrowserDriver browser=new BrowserDriver(platformVersion, deviceName, appiumServerIP, waitTime, driverPath);
			driver=browser.getdriver();
			//全局隐式等待
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			AutoLogger.log.info("启动失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}
	/**
	 *浏览器访问网页
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
	   * 基于某个元素的坐标位置，找到需要点击的坐标的位置进行操作。这样做的好处是不受分辨率
	 * xpath表达式定位id的方法：//*[@resource-id='com.tencent.mobileqq:id/ws']
	 * @param xpath 元素基于xpath定位的表达式
	 * @param xrate X轴的比例
	 * @param yrate y轴的比例
	 */
	
	public void tapByRelativeCoordinate(String xpath,String xrate,String yrate) {
		WebElement topTab=driver.findElement(By.xpath(xpath));
		//获取元素的起始点
		Point topStartP= topTab.getLocation();
		//获取元素的区域范围，整个元素的宽和高
		Dimension topD=topTab.getSize();
		System.out.println("起始点："+topStartP+"范围："+topD);
		//计算需要点击的位置处于这个元素的什么地方
		int startx=topStartP.getX();
		int starty=topStartP.getY();
		int rangex=topD.getWidth();
		int rangey=topD.getHeight();
		int x=Integer.parseInt(xrate);
		int y=Integer.parseInt(yrate);
		//x轴的十分之一的位置，和y轴的2分之一
		int targetX=startx+rangex/x;
		int targetY=starty+rangey/y;
		//使用appium的touchAction类来完成坐标点击
		TouchAction act=new TouchAction(driver);
		//完成对于坐标位置的点击，注意传递的参数是PointOption中的point静态方法完成构造的点位。
		act.tap(PointOption.point(targetX, targetY)).perform();
	}
	/**
	 * 通过xpath定位元素然后输入内容
	 * @param xpath
	 * @param text
	 */
	public void input(String xpath, String text) {
		try {
			driver.findElement(By.xpath(xpath)).clear();
			driver.findElement(By.xpath(xpath)).sendKeys(text);
			AutoLogger.log.info(xpath+"元素中输入"+text);
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			saveScrShot("input");
		}
	}
	/**
	 * 通过xpath定位元素进行点击
	 * @param xpath
	 */
	public void click(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
			AutoLogger.log.info(xpath+"元素进行点击");
		} catch (Exception e) {
			AutoLogger.log.error(e, e.fillInStackTrace());
			saveScrShot("click");
		}
	}
	/**
	 * 针对于某些可能出现也可能不出现的元素，基于trycatch的机制，尝试点击，如果没有就输出日志，没有广告不需要关闭，继续执行后续代码。
	 * @param xpath
	 */
	public void closeAd(String xpath) {
		try {
			driver.findElement(By.xpath(xpath)).click();
			AutoLogger.log.info(xpath+"元素进行点击，关闭广告");
		} catch (Exception e) {
			AutoLogger.log.info("没有广告，不需要关闭");
		}
		
	}
	    // 调用adb滑动 注意这里的时间m是毫秒
		public void adbSwipe(String i, String j, String k, String l, String m) {
			try {
				this.runCMD("adb shell input swipe " + i + " " + j + " " + k + " " + l + " " + m);
			} catch (Exception e) {
				AutoLogger.log.error("通过adb执行滑动失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		// 调用adb模拟按键
		public void adbPressKey(String keycode) {
			try {
				int k = Integer.parseInt(keycode);
				String cmd = " adb shell input keyevent " + k;
				runCMD(cmd);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				AutoLogger.log.error("通过adb执行按键事件失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		/**
		 * 调用adb点击坐标
		 */
		public void adbTap(String xAxis, String yAxis) {
			try {
				int x = Integer.parseInt(xAxis);
				int y = Integer.parseInt(yAxis);
				runCMD("adb shell input tap " + x + " " + y);
			} catch (Exception e) {
				AutoLogger.log.error("通过adb执行点击失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		/**
		 * 关闭app
		 */
		public void quitApp() {
			try {
				driver.closeApp();
			} catch (Exception e) {
				AutoLogger.log.error("关闭app失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		
		/**
		 * 通过taskkill命令杀掉appiumserver的进程。
		 */
		public void killAppium() {
			try {
				runCMD("taskkill /F /IM node.exe");
			} catch (Exception e) {
				AutoLogger.log.error("关闭appiumserver服务失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		/**
		 * 通过xpath定位元素内容 getText()断言
		 * @param xpath
		 * @param paramRes
		 */
		public void assertSame(String xpath, String paramRes) {
			try {
				String result = driver.findElement(By.xpath(xpath)).getText();
				System.out.println(result);
				if (result.equals(paramRes)) {
					AutoLogger.log.info(xpath+"断言成功，测试用例执行成功");
				} else {
					AutoLogger.log.info("测试用例执行失败");
				}
			} catch (Exception e) {
				AutoLogger.log.error("执行断言时报错");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		/**
		 * 通过xpath定位元素内容 getText()断言
		 * @param xpath
		 * @param paramRes
		 */
		public void assertByTagName(String xpath, String paramRes) {
			try {
				String result = driver.findElement(By.xpath(xpath)).getTagName();
				System.out.println(result);
				if (result.equals(paramRes)) {
					AutoLogger.log.info(xpath+"断言成功，测试用例执行成功");
				} else {
					AutoLogger.log.info("测试用例执行失败");
				}
			} catch (Exception e) {
				AutoLogger.log.error("执行断言时报错");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		
		// 通过appium的方法进行滑屏
		public void appiumSwipe(String iniX, String iniY, String finX, String finY) {
			try {
				//string型的参数转换为int型
				int x = Integer.parseInt(iniX);
				int y = Integer.parseInt(iniY);
				int x1 = Integer.parseInt(finX);
				int y1 = Integer.parseInt(finY);
				TouchAction action = new TouchAction(driver);
				//设置起点和终点
				PointOption pressPoint=PointOption.point(x, y);
				PointOption movePoint=PointOption.point(x1, y1);
				//滑动操作由长按起点->移动到终点->松开组成。
				action.press(pressPoint).moveTo(movePoint).release().perform();
			} catch (Exception e) {
				AutoLogger.log.error("执行Appium滑动方法失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		// 通过appium的方法基于元素定位进行滑屏
		public void appiumSwipeElement(String xpath, String finX, String finY) {
			try {
				//string型的参数转换为int型
				int x1 = Integer.parseInt(finX);
				int y1 = Integer.parseInt(finY);
				
				TouchAction action = new TouchAction(driver);
				//设置起点和终点
				PointOption movePoint=PointOption.point(x1, y1);
				//滑动操作由长按起点->移动到终点->松开组成。
				action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(driver.findElement(By.xpath(xpath))))).moveTo(movePoint).release().perform();
			} catch (Exception e) {
				AutoLogger.log.error("执行Appium滑动方法失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		
		// 使用appium的方法点击坐标
		public void appiumTap(String x, String y) {
			try {
				int xAxis = Integer.parseInt(x);
				int yAxis = Integer.parseInt(y);
				TouchAction action = new TouchAction(driver);
				PointOption pressPoint=PointOption.point(xAxis, yAxis);
				// action类分解动作，先长按，再移动到指定位置，再松开
				action.tap(pressPoint).release().perform();
			} catch (NumberFormatException e) {
				AutoLogger.log.error("执行Appium点击坐标方法失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		// 使用appium方法长按
		public void appiumHold(String x, String y, String time) {
			try {
				//string转int
				int xAxis = Integer.parseInt(x);
				int yAxis = Integer.parseInt(y);
				int t = Integer.parseInt(time);
				//指定长按的坐标
				PointOption pressPoint=PointOption.point(xAxis, yAxis);
				//长按的时间，使用java提供的time类库中的duration类
				Duration last = Duration.ofSeconds(t);
				WaitOptions wait=WaitOptions.waitOptions(last);
				TouchAction action = new TouchAction(driver);
				//长按坐标->指定按住的时间进行等待
				action.longPress(pressPoint).waitAction(WaitOptions.waitOptions(last)).perform();
			} catch (NumberFormatException e) {
				AutoLogger.log.error("执行Appium滑动方法失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		//长按页面上的某一个元素
		public void appiumHoldEl(String xpath,String time) {
			try {
				int t = Integer.parseInt(time);
				Duration last = Duration.ofSeconds(t);
				TouchAction action = new TouchAction(driver);
				//定位到一个元素，并且在该元素上长按指定的时长
				action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(driver.findElementByXPath(xpath))).withDuration(last)).waitAction(WaitOptions.waitOptions(last)).perform();
			} catch (NumberFormatException e) {
				AutoLogger.log.error("执行Appium滑动方法失败");
				AutoLogger.log.error(e, e.fillInStackTrace());
			}
		}
		
		/**
		 * 实现显式等待的方法，在每次定位元素时，先尝试找元素，给10秒钟的最长等待。
		 */
		public void explicityWait(String xpath) {
			try {
				WebDriverWait eWait = new WebDriverWait(driver, 10);
				eWait.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(By.xpath(xpath));
					}
				});
			} catch (Exception e) {
				AutoLogger.log.error(e,e.fillInStackTrace());
			}
		}
		

		/**
		 * 错误截图方法
		 * @param method
		 */
		public void saveScrShot(String method) {
			// 获取当前的执行时间
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd+HH-mm-ss");
			// 当前时间的字符串
			String createdate = sdf.format(date);
			// 拼接文件名，形式为：工作目录路径+方法名+执行时间.png
			String scrName = "SCRshot/" + method + createdate + ".png";
			// 以当前文件名创建文件
			File scrShot = new File(scrName);
			// 将截图保存到临时文件
			File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				Files.copy(tmp, scrShot);
			} catch (IOException e) {
				AutoLogger.log.error(e,e.fillInStackTrace());
				AutoLogger.log.error("截图失败！");
			}
		}
		/**
		 * 双指操作，需要分别指定两个手指的动作起止坐标。
		 */
		public void doubleFinger() {
			//multitouchaction，用于拼接多个touchaction，让他们同时发生。
			MultiTouchAction multiAction=new MultiTouchAction(driver);
			//创建两个touchaction，分别实现两个手指的动作。
			TouchAction actionone =new TouchAction(driver);
			//创建等待时间的对象。
			Duration last = Duration.ofMillis(300);
			WaitOptions waitOptions=WaitOptions.waitOptions(last);
			//创建第一个手指的移动起止点。x坐标增大，y坐标减小，往右上方划
			PointOption pressPointone=PointOption.point(300, 900);
			PointOption movePointone=PointOption.point(400, 800);
			//滑动操作由长按起点->移动到终点->松开组成。
			actionone.press(pressPointone).waitAction(waitOptions).moveTo(movePointone).waitAction(waitOptions).release();
			//创建第二个手指的动作。
			TouchAction actiontwo =new TouchAction(driver);
			//x坐标减小，y坐标增大，往左下方划
			PointOption pressPointtwo=PointOption.point(500, 600);
			PointOption movePointtwo=PointOption.point(400, 800);
			//滑动操作由长按起点->移动到终点->松开组成。
			actiontwo.press(pressPointtwo).waitAction(waitOptions).moveTo(movePointtwo).waitAction(waitOptions).release();
			//将创建好的两个不同的touchaction，添加到multiaction里，形成一组同步动作，从而完成操作。
			multiAction.add(actionone).add(actiontwo).perform();
			
		}
		public void tripleFinger() {
			MultiTouchAction tripleAction =new MultiTouchAction(driver);
			TouchAction actionone =new TouchAction(driver);
			//创建等待时间的对象。
			Duration last = Duration.ofMillis(300);
			WaitOptions waitOptions=WaitOptions.waitOptions(last);
			//创建第一个手指的移动起止点。x坐标增大，y坐标减小，往右上方划
			PointOption pressPointone=PointOption.point(300, 900);
			PointOption movePointone=PointOption.point(400, 800);
			//滑动操作由长按起点->移动到终点->松开组成。
			actionone.press(pressPointone).waitAction(waitOptions).moveTo(movePointone).waitAction(waitOptions).release();
			//创建第二个手指的动作。
			TouchAction actiontwo =new TouchAction(driver);
			//x坐标减小，y坐标增大，往左下方划
			PointOption pressPointtwo=PointOption.point(500, 600);
			PointOption movePointtwo=PointOption.point(400, 800);
			//滑动操作由长按起点->移动到终点->松开组成。
			actiontwo.press(pressPointtwo).waitAction(waitOptions).moveTo(movePointtwo).waitAction(waitOptions).release();
			TouchAction actionthree=new TouchAction(driver);
			PointOption pressPointthree=PointOption.point(400, 600);
			PointOption movePointthree=PointOption.point(400, 800);
			actionthree.press(pressPointthree).waitAction(waitOptions).moveTo(movePointthree).waitAction(waitOptions).release();
			//将创建好的两个不同的touchaction，添加到multiaction里，形成一组同步动作，从而完成操作。
			tripleAction.add(actionone).add(actiontwo).add(actionthree).perform();
			
		}
		/**
		 * 获取当前手机中所有的context并输出
		 */
		public void printcontexts() {
			AutoLogger.log.info(driver.getContextHandles());
		}
		
		/**
		 * 切换context
		 * @param contextName
		 */
		public void switchcontext(String contextName) {
			try {
				driver.context(contextName);
			    AutoLogger.log.info("现在的context是："+driver.getContext());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				AutoLogger.log.error(e,e.fillInStackTrace());
				AutoLogger.log.error("切换context失败");
			}
		}
		
		/**
		 * 关闭手机浏览器
		 */
		public void closeBrowser() {
			try {
				driver.quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				AutoLogger.log.error(e,e.fillInStackTrace());
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
		 * 登录qq
		 */
		public void LoginQQ() {
			input("//*[@content-desc='请输入QQ号码或手机或邮箱']", "493187431");
	        input("//*[@content-desc='密码 安全']", "ACHAO19870426");
	        click("//*[@content-desc='登 录']");
		}


}
