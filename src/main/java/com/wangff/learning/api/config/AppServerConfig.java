//package com.wangff.learning.api.config;
//
//import com.nettyrpc.registry.ServiceRegistry;
//import com.nettyrpc.server.RpcServer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//
//@Configuration
//public class AppServerConfig {
//
//    @Bean(name = "serviceRegistry")
//    public ServiceRegistry serviceRegistry () {
//        return new ServiceRegistry("127.0.0.1:2181");
//    }
//
//    @Bean(name = "rpcServer")
//    @DependsOn("serviceRegistry")
//    public RpcServer rpcServer (ServiceRegistry serviceRegistry) {
//        return new RpcServer("127.0.0.1:18080",serviceRegistry);
//    }
//}