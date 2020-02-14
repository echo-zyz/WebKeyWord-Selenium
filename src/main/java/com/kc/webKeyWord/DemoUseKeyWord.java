package com.kc.webKeyWord;

public class DemoUseKeyWord {
	public static void main(String[] args) {
//		WebkeyWord00 web=new WebkeyWord00();
//		web.openBrowser00("firefox");
//		web.visitWeb00("https://www.baidu.com/");
//		System.out.println("标题："+web.getTitle00());
//		web.inputAndSubmitByName00("wd", "cheese");
//		web.explicitlyWaitTitle00();
//		System.out.println("标题："+web.getTitle00());
//		web.closeBrowser00();
		
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		web.visitWeb("http://www.testingedu.com.cn:8000/");
		web.getAllGoods("//div[@class='cata-nav-wrap']/a");
		web.click("//a[contains(string(),'登录')]");
        web.input("//input[@name='username']", "493187431@qq.com");
        web.input("//input[@name='password']", "123456");
        web.input("//input[@name='verify_code']", "1");
        web.click("//a[@name='sbtbutton']");
        web.halt("5");
        web.click("//a[contains(text(),'安全退出')]");
	}

}
