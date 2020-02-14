package com.kc.webKeyWord;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Sleeper;

public class LoginForum {
	public static void main(String[] args) {
		WebKeyWord web=new WebKeyWord();
		web.openBrowser("chrome");
		web.visitWeb("http://www.greenyouxi.cn/bbs/forum.php");
		web.input("//input[@name='username']", "kuangchao");
		web.input("//input[@name='password']", "200531410017");
		web.click("//button[contains(string(),'登录')]");
		web.halt("10");
		web.click("//a[contains(string(),'综合交流')]");
        web.click("//a[@id=\"newspecial\"]/img");
        web.click("//*[@id=\"typeid_ctrl\"]");
        web.click("//*[@id=\"typeid_ctrl_menu\"]/ul/li[3]");
        web.input("//input[@id=\"subject\"]", "众志成城 抗击疫情");
        web.halt("10");
        web.switchToIframeByIdOrName("e_iframe");
        web.halt("10");
		web.input("//body[@contenteditable='true']", "众志成城 抗击疫情啊");
		web.halt("10");
		web.switchIframeToRoot();
		web.halt("10");
		web.click("//button[@id=\"postsubmit\"]/span");
		web.closeBrowser();
	}

}
