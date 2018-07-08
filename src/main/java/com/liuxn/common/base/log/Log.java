package com.liuxn.common.base.log;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.event.Level;

public final class Log {

	private Logger logger;

	Log(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void trace(String msg) {
		logger.trace(msg);
	}

	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);

	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}
	
	// ------------------------ others ------------------
	
	/**
	 * 线程中添加 sql 语句。一次添加一个
	 * 
	 * @param sql
	 */
	public void addSqls(String sql) {
		int i = 0;
		while (true) {
			i++;
			String key = "SQLPREFIX_" + i;
			if (MDC.get(key) == null) {
				MDC.put(key, sql);
				break;
			}
		}
	}
	
	/**
	 * 获取当前线程中储存的 sql 语句（全部）
	 * 
	 * @return
	 */
	public List<String> getSqls() {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			i++;
			String key = "SQLPREFIX_" + i;
			String string = MDC.get(key);
			if (string == null) {
				break;
			} else {
				MDC.remove(key);
				list.add(string);
			}
		}
		return list;
	}

	/**
	 * 只设置起始时间，不打印信息
	 * 
	 * @param id 用于关联起止
	 */

	public void elapseStart(String id) {
		elapseStart(null, id);
	}
	
	/**
	 * 设置起始时间，打印信息 （默认INFO)
	 * 
	 * @param msg
	 * @param id 用于关联起止
	 *            
	 */
	public void elapseStart(String msg, String id) {
		elapseStart(msg, Level.INFO, id);
	}
	

	/**
	 * 设置起始时间，打印信息（指定LEVEL)
	 * 
	 * @param msg
	 * @param level org.slf4j.event.Level
	 * @param id 用于关联起止
	 */
	public void elapseStart(String msg, Level level, String id) {
		MDC.put(id, String.valueOf(System.currentTimeMillis()));
		if (msg == null)
			return;
		if (Level.TRACE == level) {
			logger.trace(msg);
		} else if (Level.DEBUG == level) {
			logger.debug(msg);
		} else if (Level.INFO == level) {
			logger.info(msg);
		} else if (Level.WARN == level) {
			logger.warn(msg);
		} else if (Level.ERROR == level) {
			logger.error(msg);
		}
	}

	/**
	 * 只计算耗时，不打印信息
	 * 
	 * @param id 用于关联起止
	 * @return 返回耗时（毫秒）
	 */
	public long elapseEnd(String id) {
		return elapseEnd(null, id);
	}
	
	/**
	 * 计算耗时，打印信息（默认INFO)
	 * 
	 * @param msg
	 * @param id 用于关联起止
	 * @return 返回耗时（毫秒）
	 */
	public long elapseEnd(String msg, String id) {
		return elapseEnd(msg, Level.INFO, id);
	}
	
	

	/**
	 * 计算耗时，打印信息（指定LEVEL)
	 * 
	 * @param msg
	 * @param level org.slf4j.event.Level
	 * @param id 用于关联起止
	 * @return 返回耗时（毫秒）
	 */
	public long elapseEnd(String msg, Level level, String id) {
		Long start = -1L;
		try {
			String s = MDC.get(id);
			if (s != null) {
				start = Long.valueOf(s);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		long result = 0;
		if (start < 0 || start > end) {
			if (msg != null)
				logger.info(msg);
		} else {
			result = end - start;
			if (msg != null)
				logger.info(msg + " 耗时:" + result + "毫秒");
		}
		MDC.remove(id);
		return result;
	}
}
