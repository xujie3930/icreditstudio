package com.micro.cloud.sms.core.client.impl;

import static com.micro.cloud.sms.core.enums.SmsChannelEnum.ALIYUN;
import com.micro.cloud.sms.core.client.SmsClient;
import com.micro.cloud.sms.core.client.SmsClientFactory;
import com.micro.cloud.sms.core.client.impl.aliyun.AliyunSmsClient;
import com.micro.cloud.sms.core.enums.SmsChannelEnum;
import com.micro.cloud.sms.core.property.SmsChannelProperties;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

/**
 * 短信客户端工厂接口
 *
 * @author zzf
 */
@Validated
public class SmsClientFactoryImpl implements SmsClientFactory {

  private final Logger logger = LoggerFactory.getLogger(SmsClientFactoryImpl.class);

  /** 短信客户端 Map key：渠道编号，使用 {@link SmsChannelProperties#getId()} */
  private final ConcurrentMap<Long, AbstractSmsClient> channelIdClients = new ConcurrentHashMap<>();

  /**
   * 短信客户端 Map key：渠道编码，使用 {@link SmsChannelProperties#getCode()} ()}
   *
   * <p>注意，一些场景下，需要获得某个渠道类型的客户端，所以需要使用它。 例如说，解析短信接收结果，是相对通用的，不需要使用某个渠道编号的 {@link #channelIdClients}
   */
  private final ConcurrentMap<String, AbstractSmsClient> channelCodeClients =
      new ConcurrentHashMap<>();

  public SmsClientFactoryImpl() {
    // 初始化 channelCodeClients 集合
    Arrays.stream(SmsChannelEnum.values())
        .forEach(
            channel -> {
              // 创建一个空的 SmsChannelProperties 对象
              SmsChannelProperties properties = new SmsChannelProperties();
              properties.setCode(channel.getCode());
              properties.setApiKey("default");
              properties.setApiSecret("default");
              // 创建 Sms 客户端
              AbstractSmsClient smsClient = createSmsClient(properties);
              channelCodeClients.put(channel.getCode(), smsClient);
            });
  }

  @Override
  public SmsClient getSmsClient(Long channelId) {
    return channelIdClients.get(channelId);
  }

  @Override
  public SmsClient getSmsClient(String channelCode) {
    return channelCodeClients.get(channelCode);
  }

  @Override
  public void createOrUpdateSmsClient(SmsChannelProperties properties) {
    AbstractSmsClient client = channelIdClients.get(properties.getId());
    if (client == null) {
      client = this.createSmsClient(properties);
      client.init();
      channelIdClients.put(client.getId(), client);
    } else {
      client.refresh(properties);
    }
  }

  private AbstractSmsClient createSmsClient(SmsChannelProperties properties) {
    SmsChannelEnum channelEnum = SmsChannelEnum.getByCode(properties.getCode());
    Assert.notNull(channelEnum, String.format("渠道类型(%s) 为空", channelEnum));
    // 创建客户端
    if (channelEnum.getCode().equals(ALIYUN.getCode())) {
      // 默认使用阿里云
      return new AliyunSmsClient(properties);
    }
    // 创建失败，错误日志 + 抛出异常
    logger.error("[createSmsClient][配置({}) 找不到合适的客户端实现]", properties);
    throw new IllegalArgumentException(String.format("配置(%s) 找不到合适的客户端实现", properties));
  }
}
