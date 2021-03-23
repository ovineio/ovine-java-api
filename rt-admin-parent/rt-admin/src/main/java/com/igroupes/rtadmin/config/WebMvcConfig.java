package com.igroupes.rtadmin.config;

import com.igroupes.rtadmin.aop.LimitHandlerInterceptorAdapter;
import com.igroupes.rtadmin.aop.LoginUserArgumentResolver;
import com.igroupes.rtadmin.aop.LoginUserHandlerInterceptorAdapter;
import com.igroupes.rtadmin.aop.SystemLogHandlerInterceptorAdapter;
import com.igroupes.rtadmin.file.core.FileStore;
import com.igroupes.rtadmin.file.core.FileStoreFactory;
import com.igroupes.rtadmin.file.core.SimpleFileStoreConfig;
import com.igroupes.rtadmin.service.raw.SystemLogService;
import com.igroupes.rtadmin.service.raw.SystemLoginSessionService;
import com.igroupes.rtadmin.service.raw.SystemPermissionService;
import com.igroupes.rtadmin.service.raw.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProperties loginProperties;
    @Autowired
    private SystemLoginSessionService systemLoginSessionService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private SystemPermissionService systemPermissionService;
    @Autowired
    private RequestProperties requestProperties;
    @Autowired
    private FileStoreProperties storeProperties;


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }

    //添加自定义的拦截器
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        LoginUserArgumentResolver loginUserArgumentResolver = new LoginUserArgumentResolver();
        argumentResolvers.add(loginUserArgumentResolver);
    }

    @Bean
    public LoginUserHandlerInterceptorAdapter loginUserHandlerInterceptorAdapter() {
        return new LoginUserHandlerInterceptorAdapter(loginProperties, systemLoginSessionService, systemUserService);
    }

    @Bean
    public SystemLogHandlerInterceptorAdapter systemLogHandlerInterceptorAdapter() {
        return new SystemLogHandlerInterceptorAdapter(systemLogService, requestProperties);
    }

    @Bean
    public LimitHandlerInterceptorAdapter limitHandlerInterceptorAdapter() {
        return new LimitHandlerInterceptorAdapter(systemPermissionService,requestProperties);
    }

    @Bean
    public FileStore fileStore(){
        SimpleFileStoreConfig simpleFileStoreConfig = new SimpleFileStoreConfig();
        simpleFileStoreConfig.setDownloadUrlPrefix(storeProperties.getDownloadUrlPrefix());
        simpleFileStoreConfig.setFileStoreDir(storeProperties.getFileStoreDir());
        simpleFileStoreConfig.setStoreDirMaxBytes(storeProperties.getStoreDirMaxBytes());
        return FileStoreFactory.instance().fileStore(storeProperties.getType(),simpleFileStoreConfig);
    }


    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserHandlerInterceptorAdapter()).addPathPatterns("/**").excludePathPatterns("/rpc/**");
        registry.addInterceptor(systemLogHandlerInterceptorAdapter()).addPathPatterns("/**").excludePathPatterns("/rpc/**");
        registry.addInterceptor(limitHandlerInterceptorAdapter()).addPathPatterns("/**").excludePathPatterns("/rpc/**");
    }


}
