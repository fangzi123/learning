package com.wangff.learning.api.controller;

import com.alibaba.fastjson.JSON;
import com.wangff.learning.api.config.AccessLimit;
import com.wangff.learning.api.mapper.MessageSubjectUserStarMapper;
import com.wangff.learning.api.model.MessageSubjectUserStar;
import com.wangff.learning.api.service.KillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private MessageSubjectUserStarMapper messageSubjectUserStarMapper;
    @Autowired
    private KillService killService;

    @RequestMapping(value = "/web")
    public String importWeb(HttpServletRequest request,
                            HttpServletResponse response) {
        List<MessageSubjectUserStar> list=messageSubjectUserStarMapper.selectAll();
        Example example=new Example(MessageSubjectUserStar.class);
        example.createCriteria().andEqualTo("id",4);
        list=messageSubjectUserStarMapper.selectByExample(example);
        return JSON.toJSONString(list);
    }
    @AccessLimit(limit = 100,timeScope = 1)
    @RequestMapping(value = "/kill/{sid}")
    public String kill(@PathVariable int sid) {

        killService.kill(sid);
        return JSON.toJSONString(1);
    }

    @RequestMapping(value = "/kill/init/{sid}")
    public String killInit(@PathVariable int sid,@RequestParam int count) {
        killService.killInit(sid,count);
        return JSON.toJSONString(1);
    }
}
