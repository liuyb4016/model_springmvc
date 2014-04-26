package cn.liuyb.app.sync.json;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import cn.liuyb.app.common.utils.Slf4jLogUtils;

@Component
public class JsonObjectMapperConfiguration implements BeanPostProcessor {

    private static final Logger logger = Slf4jLogUtils.getLogger(JsonObjectMapperConfiguration.class);

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    	if (bean instanceof RequestMappingHandlerAdapter) {
        	RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
        	List<HttpMessageConverter<?>> converters = adapter.getMessageConverters();
            for (HttpMessageConverter<?> converter : converters) {
                if (converter instanceof MappingJacksonHttpMessageConverter) {
                    MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
                    ObjectMapper mapper = ObjectMapperHolder.getInstance().getNewMapper();
                    logger.debug("set new Object Mapper {}", mapper);
                    jsonConverter.setObjectMapper(mapper);
                }
            }
        }
        return bean;
    }


}