package com.github.ginjaninja.bb.account.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.message.ResultMessage;

@Service
@Transactional
public class CapabilityService {
	final static Logger LOG = LoggerFactory.getLogger("CapabilityService");
	
	@Autowired
	private CapabilityDAO dao;
	
	

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

}