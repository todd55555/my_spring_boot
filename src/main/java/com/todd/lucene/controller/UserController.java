package com.todd.lucene.controller;


import com.alibaba.fastjson.JSON;
import com.todd.lucene.entity.User;
import com.todd.lucene.service.IndexService;
import com.todd.lucene.service.UserService;
import com.todd.lucene.util.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/com/user/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "getUser/{userId}")
    public ReturnResult getUserById(@PathVariable Long userId){

        User user = userService.getUserById(userId);
        ReturnResult result = new ReturnResult();
        result.setSuccess(Boolean.TRUE);
        result.setMsg("请求成功");
        result.setData(user);
        return result;
    }

    @RequestMapping(value = "createIndex")
    public ReturnResult createIndex(){
        indexService.creatIndex(indexService.getUserDocuments());
        return ReturnResult.getSuccessInstance("请求成功",null);
    }

    @RequestMapping(value = "query/{userName}")
    public ReturnResult getUserByName(@PathVariable String userName){
        return ReturnResult.getSuccessInstance("请求成功", JSON.toJSONString(indexService.searchUser(userName)));
    }



}
