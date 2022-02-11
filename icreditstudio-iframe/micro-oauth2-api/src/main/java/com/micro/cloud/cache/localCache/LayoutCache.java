package com.micro.cloud.cache.localCache;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈布局方式本地映射关系缓存〉
 *
 * @author roy
 * @create 2021/11/19
 * @since 1.0.0
 */
public class LayoutCache {
  /** 1->top 2->bottom 3->left 4->right */
  public static final Map<Integer, String> LAYOUT_CACHE = new HashMap<>(16);

  static {
    LAYOUT_CACHE.put(1, "top");
    LAYOUT_CACHE.put(2, "bottom");
    LAYOUT_CACHE.put(3, "left");
    LAYOUT_CACHE.put(4, "right");
  }
}
