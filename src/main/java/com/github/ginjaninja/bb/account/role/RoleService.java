package com.github.ginjaninja.bb.account.role;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.account.role.Role;
import com.github.ginjaninja.bb.account.role.RoleDAO;
import com.github.ginjaninja.bb.message.ResultMessage;
import com.github.ginjaninja.bb.service.ServiceInterface;

@Service
@Transactional
public class RoleService implements ServiceInterface<Role>{
	final static Logger LOG = LoggerFactory.getLogger("RoleService");
	
	@Autowired
	private RoleDAO dao;
	
	

	/**
	 * Get role by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage get(Integer id) {
		Role role = dao.get(id);
		if(role != null){
			return ResultMessage.success(role);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save role 
	 * @param role 	{@link Role}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage save(Role role) {
		ResultMessage message = null;
		try{
			if(role.getId() == null){
				//set defaults
				role.fillFields();
				String result = role.checkRequired();
				//save if checkRequired message is empty
				if(result.length() == 0){
					role = dao.save(role);
					message = ResultMessage.success(role);
				}else{
					//return error with missing properties
					message = ResultMessage.missingProperties(result);
				}
			}else{
				//get stored role
				Role storedRole = dao.get(role.getId());
				if(storedRole == null){
					message = ResultMessage.notFound();
				}else{
					//fill in null fields from stored object
					role.fillFields(dao);
					//update activity date time
					role.setActivityDtTm(new Date());
					dao.update(role);
					//refetch role with parent/child entities
					role = dao.get(role.getId());
					message = ResultMessage.success(role);
				}
			}
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
		}
		return message;
	}

	/**
	 * Delete role by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		Role role = dao.get(id);
		if(role == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(role);
				message = ResultMessage.success(role);
			}catch(PersistenceException pe){
				message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
			}catch(Exception e){
				message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
			}
		}
		return message;
	}
	
	/**
	 * Get many with named query and params
	 * @param queryName {@link String}
	 * @param params	Map<String, Object>
	 * @return			{@link ResultMessage}
	 */
	@Override
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<Role> roles = dao.getMany(queryName, params);
		if(roles != null){
			return ResultMessage.success(roles);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Get many with named query
	 * @param queryName {@link String}
	 * @return			{@link ResultMessage}
	 */
	@Override
	public ResultMessage getMany(String queryName) {
		Collection<Role> roles = dao.getMany(queryName);
		if(roles != null){
			return ResultMessage.success(roles);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate role by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage deactivate(Integer id) {
		Role role = dao.get(id);
		role.setActiveInd("N");
		return this.save(role);
	}

	/**
	 * Activate role by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage activate(Integer id) {
		Role role = dao.get(id);
		role.setActiveInd("Y");
		return this.save(role);
	}

}
