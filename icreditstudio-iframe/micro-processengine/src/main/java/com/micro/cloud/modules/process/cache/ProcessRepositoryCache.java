package com.micro.cloud.modules.process.cache;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import com.micro.cloud.modules.process.constant.ProcessConstant;
import com.micro.cloud.modules.process.service.ProcessService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 〈流程资源进程内缓存〉
 *
 * @author roy
 * @create 2021/12/22
 * @since 1.0.0
 */
@Component
public class ProcessRepositoryCache {

  private final Logger logger = LoggerFactory.getLogger(ProcessRepositoryCache.class);

  public static final Map<String, Map<String, Integer>> PROCESS_STEP_CACHE =
      new ConcurrentHashMap<>(64);

  @Autowired private RepositoryService repositoryService;

  @Autowired private ProcessService processService;

  @PostConstruct
  public void initCache() {
    // 根据processKey获取流程相关资源
    List<ProcessDefinition> processDefinitionList =
        repositoryService.createProcessDefinitionQuery().latestVersion().list();
    Optional.ofNullable(processDefinitionList)
        .ifPresent(
            processDefinitions -> {
              processDefinitions.stream()
                  .forEach(
                      processDefinition -> {
                        List<Element> userTaskElements =
                            processService.getUserTaskElements(processDefinition.getKey());
                        AtomicInteger atomicStep = new AtomicInteger(1);
                        Map<String, Integer> stepMap = MapUtil.newHashMap(16);
                        userTaskElements.stream()
                            .filter(Objects::nonNull)
                            .forEach(
                                element -> {
                                  stepMap.put(
                                      element.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL),
                                      atomicStep.getAndIncrement());
                                  PROCESS_STEP_CACHE.put(processDefinition.getKey(), stepMap);
                                });
                      });
              logger.info("##### stepCache:{}", PROCESS_STEP_CACHE);
            });
  }
}
