package com.github.ginjaninja.bb.account.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ginjaninja.bb.controller.ControllerExceptionHandler;
import com.github.ginjaninja.bb.controller.ControllerInterface;
import com.github.ginjaninja.bb.message.ResultMessage;

@Controller
@RequestMapping(value={"account"})
public class AccountController extends ControllerExceptionHandler implements ControllerInterface<Account>{
	@Autowired
	private AccountService accountService;
	
	/**
	 * Unimplemented get
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(){
		return new ResponseEntity<ResultMessage>(new ResultMessage(ResultMessage.Type.ERROR, "Missing id or parameters to fetch."), 
				HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * Get account by id
	 * @param 	id {@link Integer}
	 * @return 	ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = accountService.get(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Save account from json object
	 * @param account {@link Account}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResultMessage> save(@RequestBody final Account account, BindingResult bindingResult) {
		ResultMessage message;
		HttpStatus status;
		if(bindingResult.hasErrors()){
			message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.BAD_JSON.toString());
			status = HttpStatus.BAD_REQUEST;
		}else{
		    message = accountService.save(account);
	        if(message.getType().equals(ResultMessage.Type.ERROR)){
	            status = HttpStatus.UNPROCESSABLE_ENTITY;
	        }else{
	            status = HttpStatus.OK;
	        }
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Delete account by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResultMessage> delete(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = accountService.delete(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Activate account by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/activate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> activate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = accountService.activate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Deactivate account by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/deactivate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> deactivate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = accountService.deactivate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	

}
