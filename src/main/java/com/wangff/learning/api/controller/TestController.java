package com.wangff.learning.api.controller;

import com.alibaba.fastjson.JSON;
import com.wangff.learning.api.model.MessageSubjectUserStar;
import com.wangff.learning.api.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/web")
    public String demo(HttpServletRequest request,
                            HttpServletResponse response) {

        List rlt= demoService.demo();
        return JSON.toJSONString(rlt);
    }


}
