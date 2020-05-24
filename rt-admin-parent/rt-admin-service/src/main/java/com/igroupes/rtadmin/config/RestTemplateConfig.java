package com.igroupes.rtadmin.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RestTemplateConfig {
    @Autowired
    private RestTemplateProperties configuration;
    @Autowired
    private HttpMessageConverters httpMessageConverters;

    //创建HTTP客户端工厂
    private ClientHttpRequestFactory createFactory() {
        if (configuration.getMaxTotalConnect() <= 0) {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(configuration.getConnectTimeout());
            factory.setReadTimeout(configuration.getReadTimeout());
            return factory;
        }
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(configuration.getMaxTotalConnect())
                .setMaxConnPerRoute(configuration.getMaxConnectPerRoute()).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        factory.setConnectTimeout(configuration.getConnectTimeout());
        factory.setReadTimeout(configuration.getReadTimeout());
        return factory;
    }

    //初始化RestTemplate,并加入spring的Bean工厂，由spring统一管理
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(this.createFactory());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();

        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        //加入FastJson转换器 根据使用情况进行操作，此段注释，默认使用jackson
        converterList.add(new FastJsonHttpMessageConverter());
        return restTemplate;
    }

}
