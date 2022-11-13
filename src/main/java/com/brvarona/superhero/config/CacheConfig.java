package com.brvarona.superhero.config;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
public class CacheConfig {

  @Autowired
  CacheManager cacheManager;

  public void refreshAllCaches() {
    cacheManager.getCacheNames()
        .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
  }

  @Scheduled(fixedRate = 300000) 
  public void refreshAllcachesAtIntervals() {
    log.info("Refreshing all cache");
    refreshAllCaches();
  }

}
