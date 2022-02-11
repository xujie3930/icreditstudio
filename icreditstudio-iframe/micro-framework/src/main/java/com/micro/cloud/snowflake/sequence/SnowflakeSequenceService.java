//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.snowflake.sequence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.data.redis.core.RedisTemplate;

public class SnowflakeSequenceService implements SequenceService {
  private final Integer MIN_NODE_ID = 0;
  private SnowFlakeIdWorker idWorker;
  private RedisTemplate<Object, Object> redisTemplate;
  private WorkNodeGenerate workNodeGenerate;
  private ClusterNode node;

  public SnowflakeSequenceService() {}

  public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setWorkNodeGenerate(WorkNodeGenerate workNodeGenerate) {
    this.workNodeGenerate = workNodeGenerate;
  }

  @PostConstruct
  public void init() {
    if (null == this.redisTemplate) {
      this.idWorker = new SnowFlakeIdWorker((long) this.MIN_NODE_ID, (long) this.MIN_NODE_ID);
    } else {
      this.node = this.workNodeGenerate.generate(this.redisTemplate);
      this.idWorker =
          new SnowFlakeIdWorker((long) this.node.getWorkId(), (long) this.node.getCenterId());
    }
  }

  @Override
  public String nextStringValue(String category) {
    return String.valueOf(nextValue(category));
  }

  @Override
  public Long nextValue(String category) {
    return this.idWorker.nextValue();
  }

  @Override
  public Long nextValue(String category, Long maxValue) {
    return this.idWorker.nextValue();
  }

  @PreDestroy
  public void destroy() {
    if (this.node != null) {
      this.workNodeGenerate.release(this.redisTemplate, this.node);
    }
  }
}
