package com.github.ginjaninja.bb.message;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;

/**
 * Uses MessageSource to fetch custom error messages from messages properties file
 * for BindingResults errors
 *
 */
public class MessageBuilder {
	
	
	private MessageBuilder(){
	}
	
	public static String build(MessageSource messageSource, List<ObjectError> errors){
		StringBuilder msg = new StringBuilder();
		for(ObjectError error : errors){
			msg.append(messageSource.getMessage(error, Locale.US));
		}
		return msg.toString();
	}
}
