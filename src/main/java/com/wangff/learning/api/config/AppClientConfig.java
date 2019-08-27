package com.wangff.learning.api.config;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.proxy.RpcClientProxyBeanPostProcessor;
import com.nettyrpc.registry.ServiceDiscovery;
import org.springframework.context.annotation.*;

@Configuration
public class AppClientConfig {

    @Bean(name = "serviceDiscovery")
    public ServiceDiscovery serviceDiscovery () {
        return new ServiceDiscovery("127.0.0.1:2181");
    }

    @Bean(name = "rpcClient")
    @DependsOn("serviceDiscovery")
    public RpcClient rpcClient (ServiceDiscovery serviceDiscovery) {
        return new RpcClient(serviceDiscovery);
    }

    @Bean
    public RpcClientProxyBeanPostProcessor rpcProxyBeanPostProcessor () {
        return new RpcClientProxyBeanPostProcessor();
    }

}