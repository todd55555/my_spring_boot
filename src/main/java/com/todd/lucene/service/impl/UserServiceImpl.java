package com.todd.lucene.service.impl;

import com.todd.lucene.dao.UserMapper;
import com.todd.lucene.entity.BookExample;
import com.todd.lucene.entity.User;
import com.todd.lucene.entity.UserExample;
import com.todd.lucene.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID获取用户信息
     * @param userId
     * @return
     */
    @Override
    public User getUserById(Long userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> getAllUsers() {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(2L);
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(ids);
        return userMapper.selectByExample(example);
    }






}
