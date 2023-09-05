package com.photoapp.api.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testmessage")
public class TestController {
@Autowired
	Environment environment;
	
	public String hello() {
		return ""+environment.getProperty("token.expriration_time");
	}
}
