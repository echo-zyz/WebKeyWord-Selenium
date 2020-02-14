package com.kc.poPattern;

import com.kc.homework.shopBackstageLoadupGoods;
import com.kc.homework.shopBuySomething;
import com.kc.webKeyWord.WebKeyWord;
import com.kc.webKeyWord.shopBackstageTest;

public class POPattern {
	public static void main(String[] args) {
		//实例化
		WebKeyWord web=new WebKeyWord();
		//打开浏览器
		web.openBrowser("chrome");
		//登录商城后台
		shopBackstageTest.shopBackstageLogin(web);
		//删除老旧商品
		//shopBackstageTest.deleteGood(web);
		//添加商品
		String goodName="量子手机六代";
		shopBackstageTest.addGood(web, goodName);
		//断言添加商品是否成功
		web.assertPageContains(goodName);
		//测试前端购买登录
		shopBuySomething.loginShop(web);
		//测试前端购买
		shopBuySomething.buyGood(web);
		//断言是否购买提交订单成功
		web.assertContainsEleText("//div[@class='erhuh']/h3", "订单提交成功，请您尽快付款！");
		web.halt("10");
		web.closeBrowser();
	}

}
