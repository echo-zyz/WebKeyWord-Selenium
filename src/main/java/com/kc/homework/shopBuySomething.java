package com.kc.homework;

import com.kc.autoLoger.AutoLogger;
import com.kc.webKeyWord.WebKeyWord;

public class shopBuySomething {
	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		//登录商城前端
		web.loginShop();
		//购买商品
		buyGood(web);
		//断言是否提交订单成功
		web.assertContainsEleText("//div[@class='erhuh']/h3", "订单提交成功，请您尽快付款！");
		web.halt("3");
		web.closeBrowser();

	}
	public static void loginShop(WebKeyWord web) {
		try {
			AutoLogger.log.info("开始登录电商前端");
			web.visitWeb("http://www.testingedu.com.cn:8000");
			web.click("//a[contains(string(),'登录')]");
			web.input("//input[@name='username']", "493187431@qq.com");
			web.input("//input[@name='password']", "123456");
			web.input("//input[@name='verify_code']", "1");
			web.click("//a[@name='sbtbutton']");
			web.halt("5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			AutoLogger.log.info("电商前端登录失败");
			AutoLogger.log.error(e,e.fillInStackTrace());
		}
	}

	public static void buyGood(WebKeyWord web) {
		web.click("//a[text()='返回商城首页']");
		//hover()鼠标悬停
		web.hover("//a[text()='全部商品分类']");
		web.halt("5");
		web.hover("//a[text()='手机数码']");
		web.halt("5");
		web.click("//dd[@class='clearfix']/a[text()='手机']");
		//切换网页,通过标题
		web.switchWindowByTitle("商品列表");
		web.halt("10");
		web.click("//span[text()='全网通3G+32G']");
		web.click("//span[text()='幻夜色']");
		web.halt("5");
		web.click("//div[@class='p-btn']/a[text()='加入购物车']");
		web.halt("10");
		web.click("//a[text()='加入购物车']");
		web.halt("10");
		//切换到iframe页面
		//web.switchIframe("//div[@class='layui-layer-content']/iframe");
		web.switchToIframeByIdOrName("layui-layer-iframe1");
		web.halt("10");
		web.click("//a[text()='去购物车结算']");
		web.click("//a[text()='去结算']");
		web.input("//textarea", "请加急配送！");
		web.halt("10");
		//利用js把网页滚动到最下面
		web.scrollToEnd();
		web.click("//button[@type='submit']");
		web.halt("10");
	}

}
