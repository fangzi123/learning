/*
 * Copyright (c) 2019 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wangff.learning.designpatterns.chain.chain1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public interface Request {

    /**
     * The msg param is the real request content to sent by netty.
     * For http protocols, the msg is an instance of {@link FullHttpRequest}.
     * For tcp protocols, the msg may be an instance of byte[].
     *
     */
    Object getMsg();

    void setMsg(Object o);

    /**
     * used to find RpcFuture, application can not set it.
     * @return rpc future id
     */
    long getCorrelationId();

    void setCorrelationId(long correlationId);

    /**
     * used to identify request for application, application can set it.
     * @return application request id
     */
    long getLogId();

    void setLogId(long logId);

    Object getTarget();

    void setTarget(Object obj);

    Method getTargetMethod();

    void setTargetMethod(Method method);



    String getServiceName();

    void setServiceName(String serviceName);

    String getMethodName();

    void setMethodName(String methodName);

    Object[] getArgs();

    void setArgs(Object[] newArgs);

    Map<String, Object> getKvAttachment();

    void setKvAttachment(Map<String, Object> requestKvAttachment);

    ByteBuf getBinaryAttachment();

    void setBinaryAttachment(ByteBuf requestBinaryAttachment);

    int getCompressType();

    void setCompressType(int number);



    Channel getChannel();

    void setChannel(Channel channel);



    Request retain();

    void release();

    void reset();

    String getAuth();

    void setAuth(String auth);

    Long getTraceId();

    void setTraceId(Long traceId);

    Long getSpanId();

    void setSpanId(Long spanId);

    Long getParentSpanId();

    void setParentSpanId(Long parentSpanId);


    String getServiceTag();

    void setServiceTag(String serviceTag);


    Integer getReadTimeoutMillis();

    void setReadTimeoutMillis(Integer readTimeoutMillis);

    Integer getWriteTimeoutMillis();

    void setWriteTimeoutMillis(Integer writeTimeoutMillis);

    void setClientName(String clientName);

    String getClientName();

    boolean isOneWay();

    void setOneWay(boolean oneWay);
}
