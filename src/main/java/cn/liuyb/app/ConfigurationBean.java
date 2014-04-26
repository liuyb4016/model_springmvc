package cn.liuyb.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.liuyb.app.common.cache.MemCached;
import cn.liuyb.app.common.domain.PathConfig;

@Configuration
public class ConfigurationBean {
	
    @Autowired
    public PathConfig pathConfig(PathConfig pathConfig) {
    	return PathConfig.INSTANCE = pathConfig;
    }
    
    public @Bean MemCached memCached() {
		return MemCached.INSTANCE;
	}
}
