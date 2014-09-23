package com.github.ginjaninja.bb.account.userRole;

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
import com.github.ginjaninja.bb.service.ServiceInterface;

@Service
@Transactional
public class UserRoleService implements ServiceInterface<UserRole>{
	final static Logger LOG = LoggerFactory.getLogger("UserRoleService");
	
	@Autowired
	private UserRoleDAO dao;
	
	

	/**
	 * Get userRole by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage get(Integer id) {
		UserRole userRole = dao.get(id);
		if(userRole != null){
			return ResultMessage.success(userRole);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save userRole 
	 * @param userRole 	{@link UserRole}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage save(UserRole userRole) {
		ResultMessage message = null;
		try{
			if(userRole.getId() == null){
				//set defaults
				userRole.fillFields();
				String result = userRole.checkRequired();
				//save if checkRequired message is empty
				if(result.length() == 0){
					userRole = dao.save(userRole);
					message = ResultMessage.success(userRole);
				}else{
					//return error with missing properties
					message = ResultMessage.missingProperties(result);
				}
			}else{
				//get stored userRole
				UserRole storedUserRole = dao.get(userRole.getId());
				if(storedUserRole == null){
					message = ResultMessage.notFound();
				}else{
					//fill in null fields from stored object
					userRole.fillFields(dao);
					//update activity date time
					userRole.setActivityDtTm(new Date());
					dao.update(userRole);
					//refetch userRole with parent/child entities
					userRole = dao.get(userRole.getId());
					message = ResultMessage.success(userRole);
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
	 * Delete userRole by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		UserRole userRole = dao.get(id);
		if(userRole == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(userRole);
				message = ResultMessage.success(userRole);
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
		Collection<UserRole> userRoles = dao.getMany(queryName, params);
		if(userRoles != null){
			return ResultMessage.success(userRoles);
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
		Collection<UserRole> userRoles = dao.getMany(queryName);
		if(userRoles != null){
			return ResultMessage.success(userRoles);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate userRole by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage deactivate(Integer id) {
		UserRole userRole = dao.get(id);
		userRole.setActiveInd("N");
		return this.save(userRole);
	}

	/**
	 * Activate userRole by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage activate(Integer id) {
		UserRole userRole = dao.get(id);
		userRole.setActiveInd("Y");
		return this.save(userRole);
	}

	
}
