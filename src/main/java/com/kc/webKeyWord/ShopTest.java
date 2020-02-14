package com.kc.webKeyWord;

public class ShopTest {

	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		web.visitWeb("http://www.testingedu.com.cn:8000/");
		web.loginShop();
		web.halt("5");
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
		//断言是否提交订单成功
		web.assertContainsEleText("//div[@class='erhuh']/h3", "订单提交成功，请您尽快付款！");
		web.halt("3");
		web.closeBrowser();

	}

}
