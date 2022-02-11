//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;

public class RandomNodeGenerate implements WorkNodeGenerate {
  private final int workerIdBits = 5;
  private final Integer MAX_NODE_ID = 31;
  private final Integer MIN_NODE_ID = 0;
  private static String DATA_CENTER_KEY = "DATA_CENTER_KEY";
  private static String WORK_ID_KEY_PREFIXX = "WORK_ID_KEY::";
  private static String SNOW_FLAKE_KEY_LOCK;
  private static String SNOW_FLAKE_KEY_LOCK_VALUE;
  private static final int LOCK_TIMEOUT = 5;
  private Integer dataCenterId;

  public RandomNodeGenerate() {}

  @Override
  public ClusterNode generate(RedisTemplate<Object, Object> redisTemplate) {
    if (!this.lock(redisTemplate, 5)) {
      throw new IllegalStateException("lock timeout from redis server.");
    } else {
      this.dataCenterId = this.getDatacenterId(redisTemplate);
      int workId = this.getWorkId(redisTemplate, this.dataCenterId);
      return new ClusterNode(this.dataCenterId, workId);
    }
  }

  @Override
  public boolean release(RedisTemplate<Object, Object> redisTemplate, ClusterNode node) {
    int datacenterId = node.getCenterId();
    int workId = node.getWorkId();
    return redisTemplate
            .opsForSet()
            .remove(WORK_ID_KEY_PREFIXX + datacenterId, new Object[] {workId})
        == (long) workId;
  }

  private int getDatacenterId(RedisTemplate<Object, Object> redisTemplate) {
    Set<Object> set = redisTemplate.opsForSet().members(DATA_CENTER_KEY);
    if (set != null && !set.isEmpty()) {
      for (int i = 0; i <= this.MAX_NODE_ID; ++i) {
        if (!set.contains(i)) {
          redisTemplate.opsForSet().add(DATA_CENTER_KEY, new Object[] {i});
          return i;
        }

        Set<Object> workSet = redisTemplate.opsForSet().members(WORK_ID_KEY_PREFIXX + i);
        if (workSet == null || workSet.isEmpty() || workSet.size() <= this.MAX_NODE_ID) {
          return i;
        }
      }

      throw new IllegalStateException("have no left datacenter.");
    } else {
      redisTemplate.opsForSet().add(DATA_CENTER_KEY, new Object[] {this.MIN_NODE_ID});
      return this.MIN_NODE_ID;
    }
  }

  private int getWorkId(RedisTemplate<Object, Object> redisTemplate, int datacenterId) {
    Set<Object> workSet = redisTemplate.opsForSet().members(WORK_ID_KEY_PREFIXX + datacenterId);
    if (workSet != null && !workSet.isEmpty()) {
      for (int i = 0; i <= this.MAX_NODE_ID; ++i) {
        if (!workSet.contains(i)) {
          redisTemplate.opsForSet().add(WORK_ID_KEY_PREFIXX + datacenterId, new Object[] {i});
          return i;
        }
      }

      this.dataCenterId = this.getDatacenterId(redisTemplate);
      return this.getWorkId(redisTemplate, this.dataCenterId);
    } else {
      redisTemplate
          .opsForSet()
          .add(WORK_ID_KEY_PREFIXX + datacenterId, new Object[] {this.MIN_NODE_ID});
      return this.MIN_NODE_ID;
    }
  }

  private boolean lock(RedisTemplate<Object, Object> redisTemplate, Integer maxTimeout) {
    Integer waitTime;
    do {
      boolean ret =
          redisTemplate
              .opsForHash()
              .putIfAbsent(
                  SNOW_FLAKE_KEY_LOCK,
                  SNOW_FLAKE_KEY_LOCK_VALUE,
                  Long.toString(System.currentTimeMillis()));
      if (ret) {
        redisTemplate.expire(SNOW_FLAKE_KEY_LOCK, 5L, TimeUnit.SECONDS);
        return true;
      }

      try {
        TimeUnit.SECONDS.sleep(5L);
      } catch (InterruptedException var5) {
        var5.printStackTrace();
      }

      waitTime = maxTimeout;
      maxTimeout = maxTimeout - 1;
    } while (waitTime > 0);

    return false;
  }

  static {
    SNOW_FLAKE_KEY_LOCK = DATA_CENTER_KEY + "::LOCK";
    SNOW_FLAKE_KEY_LOCK_VALUE = SNOW_FLAKE_KEY_LOCK + "::VALUE";
  }
}
