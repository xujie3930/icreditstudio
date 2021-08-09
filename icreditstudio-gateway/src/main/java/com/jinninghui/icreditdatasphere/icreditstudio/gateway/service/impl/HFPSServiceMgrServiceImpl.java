package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.impl;

import com.jinninghui.icreditdatasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.icreditdatasphere.icreditstudio.gateway.service.result.Interface;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UAA中定义的Service的管理服务
 *
 * @author lidab
 *
 */
@Service
public class HFPSServiceMgrServiceImpl implements HFPSServiceMgrService {
	@Autowired
	private UaaService uaaService;

	/**
	 * 读写锁，用于读写service时互斥
	 */
	private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
	// 以下几个成员使用的时候会采用临时变量赋值模式，最小化锁范围
	/**
	 * 用于匹配接口service（非通配符）
	 */
	private static Map<String, Interface> ExactlyServiceMap = new HashMap<String, Interface>();
	/**
	 * 用于匹配通配符service
	 */
	private static List<Interface> WildServiceList = new ArrayList<Interface>();
	/**
	 * 通配符service对应缓存的Pattern
	 */
	public static Map<Interface, Pattern> WildServicePatternMap = new HashMap<Interface, Pattern>();

	Logger log = LoggerFactory.getLogger(HFPSServiceMgrServiceImpl.class);

	/**
	 * 调用UAA接口查询出所有Service加载至进程内缓存 会抛出异常 线程安全
	 */
	@Override
	public void loadInterfaces() {
		List<Interface> services = uaaService.getAllServices();
		Map<String, Interface> exactlyServiceMap = new HashMap<String, Interface>();
		List<Interface> wildServiceList = new ArrayList<Interface>();
		Map<Interface, Pattern> wildServicePatternMap = new HashMap<Interface, Pattern>();
		for (Interface service : services) {
			if (StringUtils.equals(service.getUriType(), Constants.UriType.INTERFACE.code)) {
				String methodStr = service.getMethod();
				String[] methods = methodStr.split(",");
				if (methods != null && methods.length > 0) {
					for (String method : methods) {
						String serviceKey = service.getUri() + ":" + method;
						exactlyServiceMap.put(serviceKey, service);
					}
				}
			} else if (StringUtils.equals(service.getUriType(), Constants.UriType.WILDCARD.code)) {
				wildServiceList.add(service);
				log.debug("add one WILDCARD serivce:" + service.toString());
				Pattern p = Pattern.compile(service.getUri());
				wildServicePatternMap.put(service, p);
			}
		}

		LOCK.writeLock().lock();
		try {// 使用临时变量wildServiceList等，最小化写锁范围
			WildServiceList = wildServiceList;
			ExactlyServiceMap = exactlyServiceMap;
			WildServicePatternMap = wildServicePatternMap;
		} finally {
			LOCK.writeLock().unlock();
		}
		log.info("loadServices Success, 通配符Service数量:"+wildServiceList.size()+",精准Service数量:"+ExactlyServiceMap.size());
	}

	/**
	 * 校验当前接口(uri+method唯一确定一个接口)是否需要执行authType类型的鉴权 线程安全
	 * 不会抛出异常，如果内部出现异常，则返回true(宁可错杀，不可放过)
	 *
	 * @param uri
	 *            当前请求地址
	 * @param method
	 *            当前请求方式
	 * @param authType
	 *            鉴权方式
	 * @return 是否需要鉴权标识
	 */
	@Override
	public boolean interfaceNeedAuth(String uri, String method, String authType) {
		LOCK.readLock().lock();
		Map<String, Interface> exactlyServiceMap = null;
		List<Interface> wildServiceList = null;
		Map<Interface, Pattern> wildServicePatternMap = null;
		try {
			exactlyServiceMap = ExactlyServiceMap;
			wildServiceList = WildServiceList;
			wildServicePatternMap = WildServicePatternMap;
		} catch (Exception e) {
			log.error("赋值ExactlyServiceMap出现异常", e);
			return true;
		} finally {
			LOCK.readLock().unlock();
		}

		try {
			boolean uriNeedAuth = false;
			String serviceKey = uri + ":" + method;
			Interface serviceInMap = exactlyServiceMap.get(serviceKey);
			if (serviceInMap != null) {
				// 当前地址需要鉴权，并且需要证书鉴权
				if (serviceInMap.getNeedAuth() == Constants.NeedAuth.NEED_AUTH.code) {
					// 如果当前地址符合接口匹配模式，并且支持入参中的鉴权类型
					if (serviceInMap.getSupportAuthType().contains(authType)) {
						uriNeedAuth = true;
					}
				}
			}
			if (!uriNeedAuth) {
				// 如果在非通配符情况下找不到匹配的鉴权service，那么查询通配符形式的
				for (Interface service : wildServiceList) {
					if (service.getNeedAuth() == Constants.NeedAuth.NEED_AUTH.code
							&& service.getSupportAuthType().contains(authType)) {
						// 如果当前地址符合通配符匹配模式，并且支持的鉴权方式包括证书鉴权
						Pattern p = wildServicePatternMap.get(service);
						Matcher m = p.matcher(uri);
						if (m.find()) {// 匹配成功,并且需要鉴权
							uriNeedAuth = true;
						}
					}
				}
			}
			return uriNeedAuth;
		} catch (Exception e) {
			log.error("interfaceNeedAuth出现出乎意料的异常,安全起见,判断该接口需要鉴权,异常信息:", e);
			return true;
		}
	}

	/**
	 * 是否匹配某个精确Service且该Service不需要鉴权
	 *
	 * @param requestURI
	 * @param method
	 * @return
	 */
	@Override
	public boolean matchOneExactlyInterface(String requestURI, String method) {
		LOCK.readLock().lock();
		try {
			Interface serviceInMap = ExactlyServiceMap.get(requestURI + ":" + method);
			if (serviceInMap != null) {
				if (serviceInMap.getNeedAuth() == Constants.NeedAuth.NOT_NEED_AUTH.code) {
					log.info("client need not auth, uri=" + requestURI + ",method=" + method);
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			log.error("matchOneExactlyService出现出乎意料的异常,安全起见,判断不存在满足条件的Service,异常信息:", ex);
			return false;
		} finally {
			LOCK.readLock().unlock();
		}
	}

	/**
	 * 是否匹配某个通配符Service且该Service不需要鉴权
	 *
	 * @param requestURI
	 * @param method
	 * @return
	 */
	@Override
	public boolean matchOneWildInterface(String requestURI, String method) {
		LOCK.readLock().lock();
		try {
			List<Interface> services = WildServiceList;
			for (Interface service : services) {
				Pattern pattern = WildServicePatternMap.get(service);
				Matcher matcher = pattern.matcher(requestURI);
				if (matcher.find() && service.getNeedAuth() == Constants.NeedAuth.NOT_NEED_AUTH.code) {
					log.info("wildcardInterface need not auth, uri=" + requestURI + ",method=" + method);
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			log.error("matchOneWildService出现出乎意料的异常,安全起见,判断不存在满足条件的Service,异常信息:", ex);
			return false;
		} finally {
			LOCK.readLock().unlock();
		}
	}
}
