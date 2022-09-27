package com.wangff.learning.common.cache;



import com.wangff.learning.common.cache.local.AutoReloadedLocalCache;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 渠道mysql表(sad_config_common)中的配置信息，加载到本地缓存，定时刷新
 *
 * @author qidawei@sensorsdata.cn
 * @version 1.0.0
 * @since 2021/6/16
 */
@Slf4j
public class DemoCache extends AutoReloadedLocalCache<String, String> {

  // 当前jar包被重新动态加载之后，static变量会重建一个
  private static final DemoCache instance = new DemoCache();

  /**
   * 单例
   */
  public static DemoCache singleton() {
    return instance;
  }

  /**
   * 停掉旧实例的刷新线程；当process jar包被重新加载之前调用
   */
  public static synchronized void stopRefreshScheduler() {
    instance.stopRefreshLocalCacheScheduler();
  }

  public DemoCache() {
    log.info("SadConfigCommonCache.new.one...");
  }

  // 这里缓存Map，是 config_type + config_key -> config_value

  // 注意，涉及到布尔值的配置，统一的用：1-true，0-false

  @Override
  protected boolean initCache(Map<String, String> cacheMap) {
    try {
      //TODO 加载
      if (!cacheMap.toString().equals(this.getMap().toString())) {
        log.info("SadConfigCommonCache.initCache.result- {}", cacheMap);
      }
      return true;
    } catch (Exception e) {
      log.error("SadConfigCommonCache.initCache.Exception: ", e);
      return false;
    }
  }

  @Override
  protected long getPeriodsForRefreshing() {
    return 30 * 1000L; // 每30秒钟刷新一次
  }


  // ------------------------ private -----------------------------------------------

  private String buildCacheKey(String configType, String configKey) {
    return configType + "@" + configKey;
  }



}
