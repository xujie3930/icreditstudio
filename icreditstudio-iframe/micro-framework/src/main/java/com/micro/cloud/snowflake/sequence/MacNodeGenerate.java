//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.util.network.NetworkUtils;
import com.micro.cloud.util.network.NetworkUtils.Filter;
import java.net.NetworkInterface;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

public class MacNodeGenerate implements WorkNodeGenerate {
  private static final String SNOW_FLAKE_WORK_ID_KEY = "SNOW_FLAKE_WORK::ID::KEY";
  private static final int LOCK_TIMEOUT = 5;
  private final int workerIdBits = 5;
  private final Integer MAX_NODE_ID = 31;
  private final Integer MIN_NODE_ID = 0;
  private static String SNOW_FLAKE_KEY_LOCK = "SNOW_FLAKE_WORK::ID::KEY::LOCK";

  public MacNodeGenerate() {}

  @Override
  public ClusterNode generate(RedisTemplate<Object, Object> redisTemplate) {
    return this.getClusterNode(this.getServer(), redisTemplate);
  }

  @Override
  public boolean release(RedisTemplate<Object, Object> redisTemplate, ClusterNode node) {
    return true;
  }

  private ClusterNode getClusterNode(String server, RedisTemplate<Object, Object> redisTemplate) {
    if (!StringUtils.hasText(server)) {
      throw new IllegalArgumentException("server can not be null.");
    } else if (redisTemplate.opsForHash().hasKey("SNOW_FLAKE_WORK::ID::KEY", server)) {
      Object value = redisTemplate.opsForHash().get("SNOW_FLAKE_WORK::ID::KEY", server);
      return (ClusterNode) JSONObject.parseObject(value.toString(), ClusterNode.class);
    } else {
      return this.getNewClusterNode(server, redisTemplate);
    }
  }

  private ClusterNode getMaxClusterNode(Map<Object, Object> entries) {
    ClusterNode maxNode = null;
    Iterator var3 = entries.entrySet().iterator();

    while (var3.hasNext()) {
      Entry<Object, Object> entry = (Entry) var3.next();
      ClusterNode its =
          (ClusterNode) JSONObject.parseObject(entry.getValue().toString(), ClusterNode.class);
      if (null == maxNode) {
        maxNode = its;
      } else if (maxNode.getCenterId() < its.getCenterId()) {
        maxNode = its;
      } else if (maxNode.getCenterId() == its.getCenterId()
          && maxNode.getWorkId() < its.getWorkId()) {
        maxNode = its;
      }
    }

    return maxNode;
  }

  private ClusterNode getNextClusterNode(Map<Object, Object> entries) {
    ClusterNode maxNode = this.getMaxClusterNode(entries);
    int centerId = maxNode.getCenterId();
    int workId = maxNode.getWorkId() + 1;
    if (workId > this.MAX_NODE_ID) {
      ++centerId;
      if (centerId > this.MAX_NODE_ID) {
        throw new IllegalStateException("CenterId max.");
      }

      workId = this.MIN_NODE_ID;
    }

    return new ClusterNode(centerId, workId);
  }

  private ClusterNode getNewClusterNode(
      String server, RedisTemplate<Object, Object> redisTemplate) {
    if (!this.lock(redisTemplate, server, 5)) {
      throw new IllegalStateException("lock timeout from redis server.");
    } else {
      Map<Object, Object> entries = redisTemplate.opsForHash().entries("SNOW_FLAKE_WORK::ID::KEY");
      ClusterNode nextNode;
      if (entries != null && !entries.isEmpty()) {
        nextNode = this.getNextClusterNode(entries);
      } else {
        nextNode = new ClusterNode(this.MIN_NODE_ID, this.MIN_NODE_ID);
      }

      redisTemplate
          .opsForHash()
          .put("SNOW_FLAKE_WORK::ID::KEY", server, JSON.toJSONString(nextNode));
      return nextNode;
    }
  }

  private boolean lock(
      RedisTemplate<Object, Object> redisTemplate, String server, Integer maxTimeout) {
    Integer waitTime;
    do {
      boolean ret =
          redisTemplate
              .opsForHash()
              .putIfAbsent(SNOW_FLAKE_KEY_LOCK, server, Long.toString(System.currentTimeMillis()));
      if (ret) {
        redisTemplate.expire(SNOW_FLAKE_KEY_LOCK, 5L, TimeUnit.SECONDS);
        return true;
      }

      try {
        TimeUnit.SECONDS.sleep(5L);
      } catch (InterruptedException var6) {
        var6.printStackTrace();
      }

      waitTime = maxTimeout;
      maxTimeout = maxTimeout - 1;
    } while (waitTime > 0);

    return false;
  }

  private String getServer() {
    Set<NetworkInterface> networkInterfaceSet =
        NetworkUtils.getNICs(new Filter[] {Filter.PHYSICAL_ONLY, Filter.UP});
    Iterator<NetworkInterface> it = networkInterfaceSet.iterator();
    String mac = "";

    while (it.hasNext()) {
      mac = NetworkUtils.getMacAddress((NetworkInterface) it.next(), "-");
      if (StringUtils.hasText(mac)) {
        break;
      }
    }

    return mac.toUpperCase();
  }
}
