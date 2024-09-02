package com.smartcontactupgrade.smartcontact;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartcontactupgrade.smartcontact.services.EmailServices;

@SpringBootTest
class SmartcontactApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private EmailServices services;
	@Test
	void sendEmailTest(){
		services.sendEmail("buntysingh397218@gmail.com", "Just Testing", "hwllo world");
	}

}
