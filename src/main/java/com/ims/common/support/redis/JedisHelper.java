package com.ims.common.support.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ims.common.util.IMSUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * 类描述： <b>Redis客户端</b>
 * 创建人：陈骑元
 * 创建时间：2016-6-2 上午12:51:44
 * 修改人：蓝枫 
 * 修改时间：2016-6-2 上午12:51:44
 * 修改备注： 
 * @version
 */
@Component
public class JedisHelper {
	private  Logger logger = LoggerFactory.getLogger(JedisHelper.class);
	 @Autowired
	 private JedisPool jedisPool;


	/**
	 * 获取Jedis连接客户端
	 * 
	 * @return
	 */
	public Jedis getJedisClient() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			logger.error("获取Redis客户端连接失败。"+e);
		}
		if (jedis == null) {
			logger.warn("没有获取到Redis客户端连接。");
		}
		return jedis;
	}

	/**
	 * 安全回收资源
	 * 
	 * @param jedis
	 */
	public  void close(Jedis jedis) {
		try {
			if(jedis!=null){
				jedis.close();
			}
		} catch (Exception e) {
			if (jedis.isConnected()) {
				jedis.quit();
				jedis.disconnect();
			}
		}
	}

	/**
	 * 设置字符串型数据
	 * 
	 * @param key
	 *            存储键
	 * @param value
	 *            存储值
	 * @param timeout
	 *            超时时间(单位：秒） 设置为0，则无时效性。
	 * @return
	 */
	public void setString(String key, String value, int timeout) {
		if (IMSUtil.isEmpty(key)) {
			throw new NullPointerException("Key不能为空!");
		}
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.set(key, value);
			if (timeout > 0) {
				jedis.expire(key, timeout);
			}
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置字符串型数据过期时间
	 * 
	 * @param key
	 *            存储键
	 * @param timeout
	 *            超时时间(单位：秒）
	 * @param key
	 */
	public void exprString(String key, int timeout) {
		if (IMSUtil.isEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.expire(key, timeout);
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置序列化对象数据
	 * 
	 * @param key
	 *            存储键
	 * @param value
	 *            存储值
	 * @param timeout
	 *            超时时间(单位：秒） 设置为0，则无时效性。
	 * @return
	 */
	public void setObj(String key, byte[] value, int timeout) {
		if (IMSUtil.isEmpty(key)) {
			throw new NullPointerException("Key不能为空!");
		}
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.set(key.getBytes(), value);
			if (timeout > 0) {
				jedis.expire(key, timeout);
			}
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取字符串型数据
	 * 
	 * @param key
	 * @return
	 */
	public  String getString(String key) {
		if (IMSUtil.isEmpty(key)) {
			throw new NullPointerException("Key不能为空!");
		}
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			value = jedis.get(key);
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * 获取序列化对象数据
	 * 
	 * @param key
	 * @return
	 */
	public  byte[] getObj(String key) {
		if (IMSUtil.isEmpty(key)) {
			throw new NullPointerException("Key不能为空!");
		}
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			value = jedis.get(key.getBytes());
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * 删除对象数据
	 * 
	 * @param key
	 */
	public  void delObj(String key) {
		if (IMSUtil.isEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.del(key.getBytes());
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 删除字符串数据
	 * 
	 * @param key
	 */
	public  void delString(String key) {
		if (IMSUtil.isEmpty(key)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.del(key);
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

	/**
	 * 清除DB
	 * 
	 * @param key
	 */
	public  void flushDB() {
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			jedis.flushDB();
			logger.info("Redsi缓存DB重置成功。");
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
	}

   /**
    * 
    * 简要说明：判断redis 是否存活 false 不在线
    * 编写者：陈骑元
    * 创建时间：2016年12月12日 上午11:47:39
    * @param 说明
    * @return 说明
    */
	public   boolean  isLive() {
		Jedis jedis = null;
		try {
			jedis = getJedisClient();
			if(jedis!=null){
				String ping=jedis.ping();
				if("PONG".equals(ping)){
					return true;
				}
			}
		
		} catch (Exception e) {
			close(jedis);
			logger.error("操作Redis失败", e);
		} finally {
			close(jedis);
		}
		return false;
	}

	
}
