package com.github.ginjaninja.bb.account.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.account.role.Role;
import com.github.ginjaninja.bb.account.role.RoleDAO;
import com.github.ginjaninja.bb.account.role.RoleService;
import com.github.ginjaninja.bb.message.ResultMessage;

@Service
@Transactional
public class CapabilityService {
	final static Logger LOG = LoggerFactory.getLogger("CapabilityService");
	
	@Autowired
	private CapabilityDAO dao;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private RoleCapabilityDAO roleCapabilityDAO;
	@Autowired
	private RoleService roleService;

	/**
	 * Get capability by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage get(Integer id) {
		Capability capability = dao.get(id);
		if(capability != null){
			CapabilityDTO capabilityDTO = new CapabilityDTO();
			capabilityDTO.convert(capability);
			return ResultMessage.success(capabilityDTO);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save capability 
	 * @param capability 	{@link Capability}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage save(Capability capability) {
		ResultMessage message = null;
		if(capability.getId() == null){
			//set defaults
			capability.fillFields();
			String result = capability.checkRequired();
			//save if checkRequired message is empty
			if(result.length() == 0){
				capability = dao.save(capability);
				CapabilityDTO capabilityDTO = new CapabilityDTO();
				capabilityDTO.convert(capability);
				message = ResultMessage.success(capabilityDTO);
			}else{
				//return error with missing properties
				message = ResultMessage.missingProperties(result);
			}
		}else{
			//get stored capability
			Capability storedCapability = dao.get(capability.getId());
			if(storedCapability == null){
				message = ResultMessage.notFound();
			}else{
				//fill in not nullable fields from stored object
				capability.fillFields(storedCapability);
				//update activity date time
				capability.setActivityDtTm(new Date());
				dao.update(capability);
				//refetch capability with parent/child entities
				capability = dao.get(capability.getId());
				CapabilityDTO capabilityDTO = new CapabilityDTO();
				capabilityDTO.convert(capability);
				message = ResultMessage.success(capabilityDTO);
			}
		}
		return message;
	}

	/**
	 * Delete capability by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		Capability capability = dao.get(id);
		if(capability == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(capability);
				message = ResultMessage.success(capability);
			}catch(PersistenceException pe){
				message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
			}catch(Exception e){
				message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
			}
		}
		return message;
	}

	/**
	 * Convert collection of Capabilitys to CapabilityDTOs
	 * @param capabilities	Collection<Capability>
	 * @return		Collection<CapabilityDTO>
	 */
	private Collection<CapabilityDTO> convertMany(Collection<Capability> capabilities){
		Collection<CapabilityDTO> dtoCapabilitys = new ArrayList<CapabilityDTO>();
		CapabilityDTO dto;
		for(Capability u : capabilities){
			dto = new CapabilityDTO();
			dto.convert(u);
			dtoCapabilitys.add(dto);
		}
		return dtoCapabilitys;
	}
	
	/**
	 * Get many with named query and params
	 * @param queryName {@link String}
	 * @param params	Map<String, Object>
	 * @return			{@link ResultMessage}
	 */
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<Capability> capabilities = dao.getMany(queryName, params);
		if(capabilities != null){
			return ResultMessage.success(this.convertMany(capabilities));
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Get many with named query
	 * @param queryName {@link String}
	 * @return			{@link ResultMessage}
	 */
	public ResultMessage getMany(String queryName) {
		Collection<Capability> capabilities = dao.getMany(queryName);
		if(capabilities != null){
			return ResultMessage.success(this.convertMany(capabilities));
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate capability by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage deactivate(Integer id) {
		Capability capability = dao.get(id);
		capability.setActiveInd("N");
		return this.save(capability);
	}

	/**
	 * Activate capability by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage activate(Integer id) {
		Capability capability = dao.get(id);
		capability.setActiveInd("Y");
		return this.save(capability);
	}
	
	/**
	 * Add capability to role
	 * @param roleId		{@link Integer}
	 * @param capabilityId	{@link Integer}
	 * @return				{@link ResultMessage} with role object as result
	 */
	public ResultMessage addCapabilityToRole(Integer roleId, Integer capabilityId){
		ResultMessage message = null;
		Role role = roleDAO.get(roleId);
		Capability capability = dao.get(capabilityId);
		//make sure role and capability exist
		if(role == null){
			message = ResultMessage.notFound();
			message.setText("Can't add capability to role. Role not found.");
		}else if(capability == null){
			message = ResultMessage.notFound();
			message.setText("Can't add capability to role. Capability not found.");
		
		//make sure role doesn't already have capability
		}else if(roleService.hasCapability(roleId, capabilityId)){
			message = ResultMessage.doesNotMeetRequirements();
			message.setText("Role already has capability.");
		}else{
			//verify that this is ok to save
			message = canSaveForRole(capability);
			
			if(message.getType().equals(ResultMessage.Type.SUCCESS)){
				//set role and capability in new roleCapability
				RoleCapability rc = new RoleCapability();
				rc.setRole(role);
				rc.setCapability(capability);
				//fill required fields
				rc.fillFields();
				//save
				roleCapabilityDAO.save(rc);
				//return success message with role (and all capabilities) as result
				Map<String, Object> params = new HashMap<>();
				params.put("id", roleId);
				message = this.getMany("getRoleCapabilities", params);
			}
		}
		return message;
	}
	
	/**
	 * Remove capability from role. Deactivate entry instead of deleting
	 * to allow for auditing.
	 * @param roleId		{@link Integer}
	 * @param capabilityId	{@link Integer}
	 * @return				{@link ResultMessage} with role object as result
	 */
	public ResultMessage removeCapability(Integer roleId, Integer capabilityId){
		ResultMessage message;
		//get roleCapability
		Map<String, Object> params = new HashMap<>();
		params.put("roleId", roleId);
		params.put("capId", capabilityId);
		Collection<RoleCapability> rcList = roleCapabilityDAO.getMany("getRoleCapability", params);
		//if exists
		if(rcList != null && !rcList.isEmpty()){
			//shouldn't be more than 1, but loop through list to be sure
			for(RoleCapability rc : rcList){
				//set active to N and update
				rc.setActiveInd("N");
				rc.fillFields();
				roleCapabilityDAO.update(rc);
			}
			message = ResultMessage.success();
		}else{
			message = ResultMessage.notFound();
			message.setText("Can't remove capability. Role doesn't have capability.");
		}
		return message;
	}
	
	
	/**
	 * Checks if capability can be saved to a (any) role and returns {@link ResultMessage} 
	 * @param capability	{@link Capability}
	 * @return				{@link ResultMessage
	 */
	private ResultMessage canSaveForRole(Capability capability){
		ResultMessage message = ResultMessage.success();
		
		//make sure cap is active and role type is ROLE
		if(capability.getActiveInd().equals("N")){
			message = ResultMessage.doesNotMeetRequirements();
		}
		//make sure cap is ROLE type
		if(capability.getType().equals(Capability.Type.ACCOUNT.toString())){
			message = ResultMessage.doesNotMeetRequirements();
			message.setText("Can't add account capability to role.");
		}
		return message;
	}
	
	
}
