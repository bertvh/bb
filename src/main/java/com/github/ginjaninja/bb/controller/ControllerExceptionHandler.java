package com.github.ginjaninja.bb.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.ginjaninja.bb.message.ResultMessage;

@Controller
public abstract class ControllerExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger("ControllerExceptionHandler");
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ResultMessage> handleTypeMismatchException(HttpServletRequest req, TypeMismatchException ex){
		LOG.error("TypeMismatchException: "+ex.getMessage());
		
		ResultMessage message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.TYPE_MISMATCH.toString());
		return new ResponseEntity<ResultMessage>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ResultMessage> handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex){
		LOG.error("IllegalArgumentException " + ex.getMessage());
		
		ResultMessage message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		return new ResponseEntity<ResultMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ResultMessage> handleNullPointerException(HttpServletRequest req, NullPointerException ex){
		LOG.error("NullPointerException " + ex.getMessage());
				
		ResultMessage message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<ResultMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
