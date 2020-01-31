package com.todd.lucene;

import com.alibaba.fastjson.JSON;
import com.todd.lucene.entity.User;
import com.todd.lucene.service.UserService;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan("com.todd.lucene.mapper")
class MySpringBootApplicationTests {

	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	public void getUser(){
		User user = userService.getUserById(1L);
		System.out.println(JSON.toJSONString(user));
	}

}
