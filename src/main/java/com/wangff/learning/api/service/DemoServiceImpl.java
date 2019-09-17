package com.wangff.learning.api.service;

import com.wangff.learning.api.mapper.MessageSubjectUserStarMapper;
import com.wangff.learning.api.model.MessageSubjectUserStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private MessageSubjectUserStarMapper messageSubjectUserStarMapper;

    @Override
    public List<MessageSubjectUserStar> demo() {
        List<MessageSubjectUserStar> list=messageSubjectUserStarMapper.selectAll();
        return list;
    }


}
