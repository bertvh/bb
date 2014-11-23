package com.github.ginjaninja.bb.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ginjaninja.bb.message.ResultMessage;

@Controller
@RequestMapping(value={"login"})
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger("LoginController");
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(){
		System.out.println("LOGIN");
		LOG.info("Login Controller");
		return new ResponseEntity<ResultMessage>(ResultMessage.success(), 
				HttpStatus.OK);
	}
	
	
}
