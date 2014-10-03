package com.github.ginjaninja.bb.account.capability;

import java.util.HashMap;
import java.util.Map;

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
@RequestMapping(value={"capability"})
public class CapabilityController extends ControllerExceptionHandler implements ControllerInterface<Capability>{
	@Autowired
	private CapabilityService capabilityService;
	
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
	 * Get capability by id
	 * @param 	id {@link Integer}
	 * @return 	ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> get(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = capabilityService.get(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Save capability from json object
	 * @param capability {@link Capability}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResultMessage> save(@RequestBody final Capability capability, BindingResult bindingResult) {
		ResultMessage message;
		HttpStatus status;
		if(bindingResult.hasErrors()){
			message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.BAD_JSON.toString());
			status = HttpStatus.BAD_REQUEST;
		}else{
		    message = capabilityService.save(capability);
	        if(message.getType().equals(ResultMessage.Type.ERROR)){
	            status = HttpStatus.UNPROCESSABLE_ENTITY;
	        }else{
	            status = HttpStatus.OK;
	        }
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Delete capability by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResultMessage> delete(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = capabilityService.delete(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.UNPROCESSABLE_ENTITY;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Activate capability by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/activate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> activate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = capabilityService.activate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}

	/**
	 * Deactivate capability by id
	 * @param id	{@link Integer}
	 * @return ResponseEntity<ResultMessage>(message, status)
	 */
	@Override
	@RequestMapping(value="/deactivate/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> deactivate(@PathVariable Integer id) {
		HttpStatus status;
		ResultMessage message = capabilityService.deactivate(id);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	/**
	 * Fetch all capabilities for role
	 */
	@RequestMapping(params="role", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResultMessage> getCapabilities(@RequestParam Integer role) {
		HttpStatus status;
		Map<String, Object> params = new HashMap<>();
		params.put("id", role);
		ResultMessage message = capabilityService.getMany("getRoleCapabilities", params);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	/**
	 * Add capability to role
	 * @param roleId		{@link Integer}
	 * @param capabilityId	{@link Integer}
	 * @return				{@link ResultMessage} with result of role and its capabilities
	 */
	@RequestMapping(value="/add", params={"role", "capability"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> addCapabilityToRole(@RequestParam(value="role") Integer roleId, 
			@RequestParam(value="capability") Integer capabilityId){
		HttpStatus status;
		ResultMessage message = capabilityService.addCapabilityToRole(roleId, capabilityId);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
	
	/**
	 * Remove capability from role
	 * @param roleId		{@link Integer}
	 * @param capabilityId	{@link Integer}
	 * @return				{@link ResultMessage} with result of role and its capabilities
	 */
	@RequestMapping(value="/remove", params={"role", "capability"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResultMessage> removeCapabilityFromRole(@RequestParam(value="role") Integer roleId, 
			@RequestParam(value="capability") Integer capabilityId){
		HttpStatus status;
		ResultMessage message = capabilityService.removeCapability(roleId, capabilityId);
		if(message.getType().equals(ResultMessage.Type.ERROR)){
			status = HttpStatus.NOT_FOUND;
		}else{
			status = HttpStatus.OK;
		}
		return new ResponseEntity<ResultMessage>(message, status);
	}
}
