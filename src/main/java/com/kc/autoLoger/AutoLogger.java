package com.kc.autoLoger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 注意log4j的日志级别有从弱到强： trace<debug<info<warn<error<fatal
 * 通常用的时候，就用info等级记录普通信息，error等级记录错误信息。
 * error如果要记录堆栈信息，使用logger.error(e,e.fillinStackTrace())。
 *
 * 使用的时候，把log4j2.xml文件（不要改名字！）放到项目的源码路径下或者maven中放到src/main/resources
 * 目录下，没有的话创建一个。 log4j2.xml中的pattern指定的是记录的格式，大家自己可以修改。
 *
 * 为了更方便的在整个项目的任意位置进行logger的调用，所以编写一个类，在其中实现一个静态变量，方便各个类进行调用完成日志记录。
 * 
 * @author admin
 *
 */
public class AutoLogger {
		//Logger logger = LogManager.getLogger(AutoLogger.class);
	//定义静态变量方便调用
	    public static Logger log=LogManager.getLogger();
}
