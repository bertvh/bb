package com.github.ginjaninja.bb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ginjaninja.bb.message.ResultMessage;
import com.google.common.base.Throwables;

@Controller
class CustomErrorController {
	private static final Logger LOG = LoggerFactory.getLogger("CustomErrorController");
	
	/**
	 * Display an error page, as defined in web.xml <code>custom-error</code> element.
	 */
	@RequestMapping("generalError")	
	@ResponseBody
	public ResponseEntity<ResultMessage> generalError(HttpServletRequest request, HttpServletResponse response, Model model) {
		// retrieve some useful information from the request
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String exceptionMessage = getExceptionMessage(throwable, statusCode);
		
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		
		ResultMessage message = ResultMessage.notFound();
		message.setText(exceptionMessage);
		return new ResponseEntity<ResultMessage>(message, HttpStatus.NOT_FOUND);
	}

	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		if (throwable != null) {
			return Throwables.getRootCause(throwable).getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
