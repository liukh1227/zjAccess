package com.zj.access.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import com.zj.base.common.Constant;
import com.zj.base.common.utils.SerializeUtils;
/**
 * Redis
 * @author:liukh
 * @date:2017-04-27 下午2:45:53
 */
public class RedisSessionManager {

	private static final Logger log = Logger.getLogger("RedisSessionManager");

	public static final String CharsetEncode = "UTF-8";
	public static final int ReadOnly = 0;
	public static final int ReadWrite = 1;

	/**
	 * 读操作
	 */
	private ShardedJedisPool shardedJedisPoolRead;
	/**
	 * 写操作
	 */
	private ShardedJedisPool shardedJedisPoolWrite;

	public String getString(String key) {
		log.info(String.format("获取[%s]的值", key));
		String value = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {

				shardedJedis = (ShardedJedis) shardedJedisPoolRead
						.getResource();
				readJedis = getReadJedis(shardedJedis);
				value = readJedis.get(key);
			}
		} catch (Exception e) {
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis get error:", e);
		} finally {
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}

		return value;
	}

	/**
	 * String 操作
	 * @author liukh
	 * @date 2016-9-5 下午3:00:13
	 * @param key
	 * @param value
	 * @return
	 */
	public String putString(String key, String value) {
		log.info(String.format("存入[%s]的值", key));
		String returnCode = null;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite
						.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.set(key, value);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis set error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}

		return returnCode;
	}

	public Object getObject(String key) {

		log.info(String.format("获取[%s]的值", key));
		Object result = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead
						.getResource();
				readJedis = getReadJedis(shardedJedis);
				byte[] resultData = readJedis.get(key.getBytes());
				result = SerializeUtils.unserialize(resultData);
			}
		} catch (Exception e) {
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis get error:", e);
		} finally {
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * object 操作
	 * @author liukh
	 * @date 2016-9-5 下午3:00:01
	 * @param key
	 * @param obj
	 * @return
	 */
	public String putObject(String key, Object obj) {
		log.info(String.format("存入[%s]的值", key));
		String returnCode = null;
		ShardedJedis shardedJedis = null;
		ByteArrayOutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite
						.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				outputStream = new ByteArrayOutputStream();
				objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(obj);
				returnCode = writeJedis.set(key.getBytes("UTF-8"),
						outputStream.toByteArray());
			}

		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis set error:", e);
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(writeJedis != null) {
				writeJedis.close();
			}
			// 返还到连接池
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}

		return returnCode;
	}

	/**
	 * object 操作
	 * @author liukh
	 * @date 2016-9-5 下午2:59:44
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public String putObject(byte[] key, byte[] value, int timeout) {
		log.info(String.format("存入[%s]的值", key));
		String returnCode = null;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && key != null) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite
						.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.set(key, value, null, null, timeout);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis set error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}

		return returnCode;
	}

	/**
	 * map操作
	 * @author liukh
	 * @date 2016-9-5 下午2:46:11
	 * @param key
	 * @param map
	 * @return
	 */
	public String putMap(String key, Map<String, String> map) {
		log.info(String.format("存入[%s]的值", key));
		String returnCode = null;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.hmset(key, map);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis hmset error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return returnCode;
	}

	/**
	 * 获取map
	 * @author liukh
	 * @date 2016-9-5 下午2:48:06
	 * @param key
	 * @param fields
	 * @return
	 */
	public Object getMap(String key, String... fields) {
		log.info(String.format("获取[%s]的值", key));
		Object result = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				result = readJedis.hmget(key, fields);
			}
		} catch (Exception e) {
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis hmget error:", e);
		} finally {
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * 删除hm
	 * @author liukh
	 * @date 2016-10-31 下午3:47:19
	 * @param key
	 * @param fields
	 * @return
	 */
	public long hdel(String key, String...fields) {
		log.info(String.format("删除[%s]的值", key));
		long result = 0;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				result = writeJedis.hdel(key, fields);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis hdel error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * hset
	 * @author liukh
	 * @date 2016-10-31 下午3:56:00
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long hset(String key, String field, String value) {
		log.info(String.format("存入[%s]的值", key));
		long returnCode = 0;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.hset(key, field, value);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis hset error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return returnCode;
	}

	/**
	 * hset
	 * @author liukh
	 * @date 2016-10-31 下午3:56:00
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public String hget(String key, String field) {
		log.info(String.format("存入[%s]的值", key));
		String value = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				value = readJedis.hget(key, field);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis hget error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return value;
	}

	/**
	 * hgetall
	 * @author liukh
	 * @date 2016-10-31 下午3:58:19
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetall(String key) {
		log.info(String.format("删除[%s]的值", key));
		Map<String, String> result = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				result = readJedis.hgetAll(key);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis hgetall error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return result;
	}

	/**
	 * list操作
	 * @author liukh
	 * @date 2016-9-5 下午2:50:58
	 * @param key
	 * @param fields
	 * @return
	 */
	
	public Long putList(String key, String... fields) {
		log.info(String.format("存入[%s]的值", key));
		Long returnCode = 0L;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.rpush(key, fields);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis rpush error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return returnCode;
	}

	/**
	 * empty list
	 * @author liukh
	 * @date 2016-9-5 下午6:46:31
	 * @param key
	 */
	public void emptyList(String key) {
		log.info(String.format("清空[%s]的值", key));
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				long len = writeJedis.llen(key);
				List<String> list = writeJedis.lrange(key, 0, len - 1);
				for(int i = 0 , n = list.size() ; i < n ; i++) {
					writeJedis.lpop(key);
				}
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis rpush error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
	}

	/**
	 * list object
	 * @author liukh
	 * @date 2016-9-5 下午3:12:59
	 * @param key
	 * @param list
	 * @return
	 */
	public Long putList(String key, List list) {
		log.info(String.format("存入[%s]的值", key));
		Long returnCode = 0L;
		ShardedJedis shardedJedis = null;
		ByteArrayOutputStream outputStream = null;
		ObjectOutputStream objectOutputStream = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				for(int i = 0 , n = list.size() ; i < n ; i++) {
					outputStream = new ByteArrayOutputStream();
					objectOutputStream = new ObjectOutputStream(outputStream);
					objectOutputStream.writeObject(list.get(i));
					returnCode = writeJedis.rpush(key.getBytes("UTF-8"), outputStream.toByteArray());
					List mlist = getObjListAll(key);
					objectOutputStream.close();
					outputStream.close();
				}
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis rpush error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return returnCode;
	}

	public List getList(String key, int start, int end) {
		log.info(String.format("获取[%s]的值", key));
		List list = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				list = readJedis.lrange(key, start, end);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis lrange error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return list;
	}

	public List getListAll(String key) {
		log.info(String.format("获取[%s]的值", key));
		List list = null;
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				long len = readJedis.llen(key);
				list = readJedis.lrange(key, 0, len - 1);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis lrange error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return list;
	}

	/**
	 * list object
	 * @author liukh
	 * @date 2016-9-5 下午3:16:58
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List getObjList(String key, int start, int end) {
		log.info(String.format("获取[%s]的值", key));
		List list = new ArrayList();
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				List<byte[]> blist = readJedis.lrange(key.getBytes(), start, end);
				for(int i = 0, n = blist.size() ; i < n ; i++) {
					list.add(SerializeUtils.unserialize(blist.get(i)));
				}
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis lrange error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return list;
	}

	public List getObjListAll(String key) {
		log.info(String.format("获取[%s]的值", key));
		List list = new ArrayList();
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				long len = readJedis.llen(key.getBytes());
				List<byte[]> blist = readJedis.lrange(key.getBytes(), 0, len - 1);
				for(int i = 0, n = blist.size() ; i < n ; i++) {
					list.add(SerializeUtils.unserialize(blist.get(i)));
				}
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis lrange error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return list;
	}

	/**
	 * set 操作
	 * @author liukh
	 * @date 2016-9-5 下午3:34:19
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long putSet(String key, String... fields) {
		log.info(String.format("存入[%s]的值", key));
		Long returnCode = 0L;
		ShardedJedis shardedJedis = null;
		Jedis writeJedis = null;
		try {
			if (shardedJedisPoolWrite != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolWrite.getResource();
				writeJedis = getWriteJedis(shardedJedis);
				returnCode = writeJedis.sadd(key, fields);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(shardedJedis);
			log.error("Redis sadd error:", e);
		} finally {
			// 返还到连接池
			if(writeJedis != null) {
				writeJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolWrite.returnResource(shardedJedis);
			}
		}
		return returnCode;
	}

	public Set getSet(String key) {
		log.info(String.format("获取[%s]的值", key));
		Set set = new HashSet();
		ShardedJedis shardedJedis = null;
		Jedis readJedis = null;
		try {
			if (shardedJedisPoolRead != null && StringUtils.isNotBlank(key)) {
				shardedJedis = (ShardedJedis) shardedJedisPoolRead.getResource();
				readJedis = getReadJedis(shardedJedis);
				set = readJedis.smembers(key);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(shardedJedis);
			log.error("Redis smembers error:", e);
		} finally {
			// 返还到连接池
			if(readJedis != null) {
				readJedis.close();
			}
			if (shardedJedis != null) {
				shardedJedisPoolRead.returnResource(shardedJedis);
			}
		}
		return set;
	}

	/**
	 * 获取读jedis
	 * @author liukh
	 * @date 2016-11-8 下午5:16:52
	 */
	public Jedis getReadJedis(ShardedJedis readShardedJedis) {
		Jedis readJedis = null;
		try {
			if (readShardedJedis != null) {
				readJedis = readShardedJedis.getShard(Constant.REDIS_SLAVE_READ_KEY);
				readJedis.select(Constant.REDIS_DB);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolRead.returnBrokenResource(readShardedJedis);
			log.error("Redis smembers error:", e);
		}
		return readJedis;
	}
	/**
	 * 获取写jedis
	 * @author liukh
	 * @date 2016-11-8 下午5:52:38
	 * @return
	 */
	public Jedis getWriteJedis(ShardedJedis writeShardedJedis) {
		Jedis writeJedis = null;
		try {
			if (writeShardedJedis != null) {
				writeJedis = writeShardedJedis.getShard(Constant.REDIS_MASTER_WRITE_KEY);
				writeJedis.select(Constant.REDIS_DB);
			}
		} catch (Exception e) {
			// 释放redis对象
			shardedJedisPoolWrite.returnBrokenResource(writeShardedJedis);
			log.error("Redis smembers error:", e);
		}
		return writeJedis;
	}
	

	public ShardedJedisPool getShardedJedisPoolRead() {
		return shardedJedisPoolRead;
	}

	public void setShardedJedisPoolRead(ShardedJedisPool shardedJedisPoolRead) {
		this.shardedJedisPoolRead = shardedJedisPoolRead;
	}

	public ShardedJedisPool getShardedJedisPoolWrite() {
		return shardedJedisPoolWrite;
	}

	public void setShardedJedisPoolWrite(ShardedJedisPool shardedJedisPoolWrite) {
		this.shardedJedisPoolWrite = shardedJedisPoolWrite;
	}

}
