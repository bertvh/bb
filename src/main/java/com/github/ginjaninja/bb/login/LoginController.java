package com.github.ginjaninja.bb.login;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static final Log LOG =  LogFactory.getLog(LoginController.class);
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(){
		System.out.println("LOGIN");
		LOG.info("Login Controller");
		return new ResponseEntity<ResultMessage>(ResultMessage.success(), 
				HttpStatus.OK);
	}
	
	
}
