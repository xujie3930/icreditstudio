//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

import org.springframework.data.redis.core.RedisTemplate;

public class SimpleNodeGenerate implements WorkNodeGenerate {
  public SimpleNodeGenerate() {
  }

  public ClusterNode generate(RedisTemplate<Object, Object> redisTemplate) {
    return new ClusterNode(0, 0);
  }

  public boolean release(RedisTemplate<Object, Object> redisTemplate, ClusterNode node) {
    return true;
  }
}
