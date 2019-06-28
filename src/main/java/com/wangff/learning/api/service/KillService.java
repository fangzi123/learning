package com.wangff.learning.api.service;

public interface KillService {
    int kill(int sid);

    void killInit(int sid,int count);
}
