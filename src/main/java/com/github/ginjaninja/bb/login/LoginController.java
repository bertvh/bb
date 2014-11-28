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
@RequestMapping("/v")
public class LoginController {
	private static final Logger LOG = LoggerFactory.getLogger("LoginController");
	
	/**
	 * Unimplemented get
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(){
		return new ResponseEntity<ResultMessage>(new ResultMessage(ResultMessage.Type.ERROR, "GET not allowed at this address"), 
				HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.POST, params={"username","password"})
	@ResponseBody
	public ResponseEntity<ResultMessage> post(){
		return new ResponseEntity<ResultMessage>(ResultMessage.success(), 
				HttpStatus.OK);
	}
}
