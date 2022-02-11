package com.micro.cloud.service;

import com.alibaba.fastjson.JSON;
import com.micro.cloud.constant.MessageConstant;
import com.micro.cloud.domain.SecurityUser;
import com.micro.cloud.domian.dto.UserDTO;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** 用户管理业务类 Created by xulei on 2021/11/3 */
@Service
public class UserServiceImpl implements UserDetailsService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public UserDetails loadUserByUsername(String userInfo) throws UsernameNotFoundException {
    //    logger.info("####### userInfo:{}", userInfo);
    // 此处获取用户信息改由业务服务传完整UserDto作为username属性进行解析
    UserDTO userDTO = JSON.parseObject(userInfo, UserDTO.class);
    if (Objects.isNull(userDTO)) {
      throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
    }
    SecurityUser securityUser = new SecurityUser(userDTO);
//    logger.info("########## security:{}", securityUser);
    return securityUser;
  }
}
