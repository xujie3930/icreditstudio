//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

import org.springframework.data.redis.core.RedisTemplate;

public interface WorkNodeGenerate {
  ClusterNode generate(RedisTemplate<Object, Object> var1);

  boolean release(RedisTemplate<Object, Object> var1, ClusterNode var2);
}
