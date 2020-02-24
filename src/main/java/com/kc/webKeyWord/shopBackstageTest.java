package com.kc.webKeyWord;

import com.kc.autoLoger.AutoLogger;

public class shopBackstageTest {
	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		AutoLogger.log.info("===============测试开始=================");
		web.openBrowser("chrome");
		//登录商城后台
		shopBackstageLogin(web);
		//删除商品
//		deleteGood(web);
		web.halt("3");
		//添加商品
		String goodName="量子手机七代";
		addGood(web,goodName);
        web.halt("5");
//        //web.assertContainsEleText("//div[text()='量子纠缠手机三代']", "量子纠缠手机三代");
//        web.assertPageContains(goodName);
//        web.halt("30");
//        web.closeBrowser();
	
	
	}

	public static void shopBackstageLogin(WebKeyWord web) {
		web.visitWeb("http://www.testingedu.com.cn:8000/Admin/Admin/login");
		web.input("//input[@name='username']", "admin");
		web.input("//input[@name='password']", "123456");
		web.input("//input[@name='vertify']", "123456");
		web.click("//input[@name='submit']");
		web.halt("5");
	}

	public static void addGood(WebKeyWord web,String goodName) {
		web.click("//span[text()='添加商品']");
		web.halt("5");
		web.input("//input[@name='goods_name']", goodName);
		web.halt("10");
		web.selectByText("(//select)[1]","手机");
		web.halt("5");
		web.selectByText("//select[@name='cat_id_2']","手机通讯" );
		web.halt("5");
		web.selectByText("//select[@name='cat_id_3']", "手机");
		web.input("//input[@name='shop_price']", "998");
		web.input("//input[@name='market_price']", "999999");
		//先点击弹出iframe
		web.click("//input[@title='点击前方预览图可查看大图，点击按钮选择文件并提交表单后上传生效']");
		web.halt("5");
		//切换到iframe
		web.switchToIframeByIdOrName("layui-layer-iframe1");
		web.halt("5");
		//主语uploadeFile要定位到input元素
		web.uploadFile("//div[@id='filePicker']//input[@type='file']", "C:\\Users\\admin\\Desktop\\1.jpg");
		web.halt("5");
		web.click("//div[text()='确定使用']");
        //上传图片完成返回主页面 然后重新切换到第一个iframe
		web.switchIframeToRoot();
		web.switchToIframeByIdOrName("workspace");
		web.click("//label[@id='is_free_shipping_label_0']");
		web.halt("5");
		web.selectByValue("//select[@name=\"template_id\"]", "6");
		web.input("//input[@name=\"virtual_sales_sum\"]", "124578");
		web.input("//input[@name=\"virtual_collect_sum\"]", "5424147");
		//切换到iframe
		web.switchToIframeByIdOrName("ueditor_0");
		//注意类似论坛的iframe框输入定位元素是body不是p
		web.input("//body[@class=\"view\"]", "第一款量子手机，完全意义上实现无延迟通信、下载和上传。只要998带回家！");
		//通过js往富文本框中输入内容
//		String jsresult=web.getJsReturnWithArg("arguments[0].innerText=\"这是VIP05的测试商品\"", "//p");
//		System.out.println(jsresult);
		//切回主窗口 再回到第一个iframe
		web.switchIframeToRoot();
		web.switchToIframeByIdOrName("workspace");
        web.click("//a[@id='submit']");
        web.halt("5");
	}

	public static void deleteGood(WebKeyWord web) {
		web.click("//a[text()='商城']");
		web.halt("5");
		web.switchToIframeByIdOrName("workspace");
		web.hover("//td/div[text()='263']/../preceding-sibling::td[1]");
		web.halt("5");
		web.click("//td/div[text()='263']/../preceding-sibling::td[1]//a[text()='删除商品']");
		web.halt("3");
		web.click("//a[text()='确定']");
		web.halt("3");
	}

}
