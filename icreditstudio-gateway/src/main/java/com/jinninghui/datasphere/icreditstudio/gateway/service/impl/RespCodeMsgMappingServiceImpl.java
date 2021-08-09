package com.jinninghui.datasphere.icreditstudio.gateway.service.impl;

import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.service.RespCodeMsgMappingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 负责映射各种响应码的code和描述 这些code和msg在各个后端服务启动时写至Redis，API网关启动时从Redis中读取
 * 
 * @author lidab
 *
 */
@Service
public class RespCodeMsgMappingServiceImpl implements RespCodeMsgMappingService {

	static Logger log = LoggerFactory.getLogger(RespCodeMsgMappingServiceImpl.class);
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	public static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

	private Map<String, String> codeMsgMap = new HashMap<>();

	/**
	 * 线程安全
	 */
	@Override
	public void loadMappings() {
		Set<Object> codes = redisTemplate.opsForHash().keys("SystemCode");
		Map<String, String> tmpMap = new HashMap<>();
		for (Object object : codes) {
			String code = (String) object;
			String message = (String) redisTemplate.opsForHash().get("SystemCode", code);
			if (StringUtils.isNotBlank(message)) {
				tmpMap.put(code, message);
			}
		}
		int size = tmpMap.size();
		LOCK.writeLock().lock();
		codeMsgMap = tmpMap;// 这个赋值应该是原子的，但是据说CPU优化可能导致问题，所以还是谨慎点吧
		LOCK.writeLock().unlock();
		log.info("end loadMappings, codeMsgMap.size=" + size);
	}

	/*
	 * 线程安全
	 */
	@Override
	public String getMsgByCode(String code) {
		LOCK.readLock().lock();
		try {
			String msg = codeMsgMap.get(code);
			if(msg!=null)
			{
				return msg;
			}
			else
			{
				return Constants.ErrorCode.SYSTEM_FAIL.comment;
			}
		} finally {
			LOCK.readLock().unlock();
		}

	}
}
