package com.todd.lucene.service;

import com.todd.lucene.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    /**
     * 根据ID获取用户信息
     * @param userId
     * @return
     */
    public User getUserById(Long userId);

    /**
     * 获取所有用户信息
     * @return
     */
    public List<User> getAllUsers();



}
