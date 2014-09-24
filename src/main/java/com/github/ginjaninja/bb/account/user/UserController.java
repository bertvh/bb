package com.github.ginjaninja.bb.account.user;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ginjaninja.bb.controller.ControllerExceptionHandler;
import com.github.ginjaninja.bb.controller.ControllerInterface;
import com.github.ginjaninja.bb.message.ResultMessage;

@Controller
@RequestMapping(value={"user"})
public class UserController extends ControllerExceptionHandler implements ControllerInterface<User>{
	@Autowired
	private UserService userService;
	
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
	 * Get user by id
	 * @param 	id {@link Integer}
	 * @return 	ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = userService.get(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Save user from json object
	 * @param user {@link User}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResultMessage> save(@RequestBody final User user, BindingResult bindingResult) {
		ResultMessage message;
		HttpStatus status;
		if(bindingResult.hasErrors()){
			message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.BAD_JSON.toString());
			status = HttpStatus.BAD_REQUEST;
		}else{
		    message = userService.save(user);
	        if(message.getType().equals(ResultMessage.Type.ERROR)){
	            status = HttpStatus.UNPROCESSABLE_ENTITY;
	        }else{
	            status = HttpStatus.OK;
	        }
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Delete user by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResultMessage> delete(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = userService.delete(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Activate user by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/activate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> activate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = userService.activate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Deactivate user by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/deactivate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> deactivate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = userService.deactivate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	@RequestMapping(value="/addaccount", params={"user", "account"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> addToAccount(@RequestParam(value="user") Integer userId, 
			@RequestParam(value="account") Integer accountId){
		HttpStatus status;
		ResultMessage message = userService.addAccount(userId, accountId);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	@RequestMapping(value="/setrole", params={"user", "role"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> addRole(@RequestParam(value="user") Integer userId, 
			@RequestParam(value="role") Integer roleId){
		HttpStatus status;
		ResultMessage message = userService.addRole(userId, roleId);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
}
