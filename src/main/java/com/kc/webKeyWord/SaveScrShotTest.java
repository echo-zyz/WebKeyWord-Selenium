package com.kc.webKeyWord;

public class SaveScrShotTest {
	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		web.visitWeb("https://www.baidu.com/");
		web.input("111", "2222");
		//web.closeBrowser();
	}
	

}
