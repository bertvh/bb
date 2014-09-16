package com.github.ginjaninja.bb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.github.ginjaninja.bb.message.ResultMessage;

public interface ControllerInterface <T> {

	public ResponseEntity<ResultMessage> get(Integer id);
	
	public ResponseEntity<ResultMessage> save(T t, BindingResult bindingResult);
	
	public ResponseEntity<ResultMessage> delete(Integer id);
	
	public ResponseEntity<ResultMessage> activate(Integer id);
	
	public ResponseEntity<ResultMessage> deactivate(Integer id);
}
