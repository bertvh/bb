package com.github.ginjaninja.bb.home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ginjaninja.bb.message.ResultMessage;

/**
 * Controller for requests to /
 *
 */
@Controller
@RequestMapping(value={"/"})
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> index() {
	    ResultMessage message = new ResultMessage(ResultMessage.Type.SUCCESS, "You are home.");
        return new ResponseEntity<ResultMessage>(message, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> teapotIndex() {
	    ResultMessage message = new ResultMessage(ResultMessage.Type.WARNING, "Be careful, teapots can be dangerous.");
		return new ResponseEntity<ResultMessage>(message, HttpStatus.I_AM_A_TEAPOT);
	}
	
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.DELETE})
	@ResponseBody
    public ResponseEntity<ResultMessage> unimplementedIndex() {
	    ResultMessage message = new ResultMessage(ResultMessage.Type.ERROR, "Reqest methods PUT and DELETE are not implemented for this address.");
        return new ResponseEntity<ResultMessage>(message, HttpStatus.NOT_IMPLEMENTED);
    }
}
