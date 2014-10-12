package com.github.ginjaninja.bb.account.role;

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
@RequestMapping(value={"role"})
public class RoleController extends ControllerExceptionHandler implements ControllerInterface<Role>{
	@Autowired
	private RoleService roleService;
	
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
	 * Get role by id
	 * @param 	id {@link Integer}
	 * @return 	ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = roleService.get(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Save role from json object
	 * @param role {@link Role}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResultMessage> save(@RequestBody final Role role, BindingResult bindingResult) {
		ResultMessage message;
		HttpStatus status;
		if(bindingResult.hasErrors()){
			message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.BAD_JSON.toString());
			status = HttpStatus.BAD_REQUEST;
		}else{
		    message = roleService.save(role);
	        if(message.getType().equals(ResultMessage.Type.ERROR)){
	            status = HttpStatus.UNPROCESSABLE_ENTITY;
	        }else{
	            status = HttpStatus.OK;
	        }
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Delete role by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResultMessage> delete(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = roleService.delete(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Activate role by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/activate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> activate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = roleService.activate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Deactivate role by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/deactivate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> deactivate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = roleService.deactivate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	
	
}
