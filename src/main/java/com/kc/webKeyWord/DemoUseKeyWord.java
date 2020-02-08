package com.kc.webKeyWord;

public class DemoUseKeyWord {
	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		web.visitWeb("https://www.baidu.com/");
		System.out.println("标题："+web.getTitle());
		web.inputAndSubmitByname("wd", "cheese");
		web.explicitlyWaitTitle();
		System.out.println("标题："+web.getTitle());
		web.closeBrowser();
	}

}
