package com.wangff.learning.api.controller;

import com.alibaba.fastjson.JSON;
import com.wangff.learning.api.mapper.MessageSubjectUserStarMapper;
import com.wangff.learning.api.model.MessageSubjectUserStar;
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
    private MessageSubjectUserStarMapper messageSubjectUserStarMapper;

    @RequestMapping(value = "/web")
    public String importWeb(HttpServletRequest request,
                            HttpServletResponse response) {
        List<MessageSubjectUserStar> list=messageSubjectUserStarMapper.selectAll();
        Example example=new Example(MessageSubjectUserStar.class);
        example.createCriteria().andEqualTo("id",4);
        list=messageSubjectUserStarMapper.selectByExample(example);
        return JSON.toJSONString(list);
    }
}
