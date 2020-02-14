package com.kc.webKeyWord;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class TestLog4j {
	public static void main(String[] args) {
		/**
		 * 注意log4j的日志级别有从弱到强：
	trace<debug<info<warn<error<fatal
		通常用的时候，就用info等级记录普通信息，error等级记录错误信息。
		error如果要记录堆栈信息，使用logger.error(e,e.fillinStackTrace())。

	使用的时候，把log4j2.xml文件（不要改名字！）放到项目的源码路径下或者maven中放到src/main/resources 目录下，没有的话创建一个。
	log4j2.xml中的pattern指定的是记录的格式，大家自己可以修改。

	为了更方便的在整个项目的任意位置进行logger的调用，所以编写一个类，在其中实现一个静态变量，方便各个类进行调用完成日志记录。
		 */
		//创建log4j对象
		Logger logger=LogManager.getLogger(TestLog4j.class);
		logger.trace("this is trace");
		logger.debug("this is debug");
		logger.info("this is an info");
		logger.info("==================测试开始=======================");
		logger.warn("this is a warning");
		try {
			Integer.parseInt("ss");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e,e.fillInStackTrace());
		}
		logger.fatal("this is a fatal error");
		return;
		
		
	}

}
