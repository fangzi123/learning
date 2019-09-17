/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */

package com.wangff.learning.designpatterns.chain.chain1;

import io.netty.buffer.ByteBuf;

import java.util.Map;

public interface Response {
    Object getResult();

    void setResult(Object result);

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

    Throwable getException();

    void setException(Throwable exception);


    Map<String, Object> getKvAttachment();

    void setKvAttachment(Map<String, Object> kvAttachment);

    ByteBuf getBinaryAttachment();

    void setBinaryAttachment(ByteBuf binaryAttachment);

    int getCompressType();

    void setCompressType(int compressType);

    void reset();
}
