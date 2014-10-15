package com.github.ginjaninja.bb.account.user;

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

import com.github.ginjaninja.bb.account.account.Account;
import com.github.ginjaninja.bb.account.account.AccountDAO;
import com.github.ginjaninja.bb.account.role.Role;
import com.github.ginjaninja.bb.account.role.RoleDAO;
import com.github.ginjaninja.bb.message.ResultMessage;

@Service
@Transactional
public class UserService {
	private static final Logger LOG = LoggerFactory.getLogger("UserService");
	
	@Autowired
	private UserDAO dao;
	@Autowired
	private AccountDAO acctDao;
	@Autowired
	private RoleDAO roleDAO;
	
	/**
	 * Get user by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage get(Integer id) {
		User user = dao.get(id);
		if(user != null){
			UserDTO userDTO = new UserDTO();
			userDTO.convert(user);
			return ResultMessage.success(userDTO);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save user 
	 * @param user 	{@link User}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage save(User user) {
		ResultMessage message = null;
		if(user.getId() == null){
			//set defaults
			user.fillFields();
			String result = user.checkRequired();
			//save if checkRequired message is empty
			if(result.length() == 0){
				user = dao.save(user);
				UserDTO userDTO = new UserDTO();
				userDTO.convert(user);
				message = ResultMessage.success(userDTO);
			}else{
				//return error with missing properties
				message = ResultMessage.missingProperties(result);
			}
		}else{
			//get stored user
			User storedUser = dao.get(user.getId());
			if(storedUser == null){
				message = ResultMessage.notFound();
			}else{
				//fill in not nullable fields from stored object
				user.fillFields(storedUser);
				
				//set acct and role if none defined
				if(user.getAccount() != null && user.getAccount().getId() != null){
					user.setAccount(acctDao.get(user.getAccount().getId()));
				}else{
					user.setAccount(storedUser.getAccount());
				}
				if(user.getRole() != null && user.getRole().getId() != null){
					user.setRole(roleDAO.get(user.getRole().getId()));
				}else{
					user.setRole(storedUser.getRole());
				}
				
					//update activity date time
				user.setActivityDtTm(new Date());
				dao.update(user);
				//refetch user with parent/child entities
				user = dao.get(user.getId());
				UserDTO userDTO = new UserDTO();
				userDTO.convert(user);
				message = ResultMessage.success(userDTO);
			}
		}
		
		return message;
	}

	/**
	 * Delete user by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		User user = dao.get(id);
		if(user == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(user);
				message = ResultMessage.success();
			}catch(PersistenceException pe){
				LOG.error("Error deleting User", pe);
				message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
			}catch(Exception e){
				LOG.error("Error deleting User", e);
				message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
			}
		}
		return message;
	}
	
	/**
	 * Convert collection of Users to UserDTOs
	 * @param users	Collection<User>
	 * @return		Collection<UserDTO>
	 */
	private Collection<UserDTO> convertMany(Collection<User> users){
		Collection<UserDTO> dtoUsers = new ArrayList<UserDTO>();
		UserDTO dto;
		for(User u : users){
			dto = new UserDTO();
			dto.convert(u);
			dtoUsers.add(dto);
		}
		return dtoUsers;
	}
	
	/**
	 * Get many with named query and params
	 * @param queryName {@link String}
	 * @param params	Map<String, Object>
	 * @return			{@link ResultMessage}
	 */
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<User> users = dao.getMany(queryName, params);
		if(users != null){
			return ResultMessage.success(this.convertMany(users));
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
		Collection<User> users = dao.getMany(queryName);
		if(users != null){
			return ResultMessage.success(this.convertMany(users));
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage deactivate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("N");
		return this.save(user);
	}

	/**
	 * Activate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage activate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("Y");
		return this.save(user);
	}

	/**
	 * Add user to account
	 * @param userId		{@link Integer} user id
	 * @param accountId		{@link Integer}	account id
	 * @return				{@link ResultMessage}
	 */
	public ResultMessage addAccount(Integer userId, Integer accountId){
		ResultMessage message;
		//make sure user and account exist
		User user = dao.get(userId);
		Account acct = acctDao.get(accountId);
		if(user == null){
			message = ResultMessage.notFound();
			message.setText("Can't add user to account. User not found.");
		}else if(acct == null){
			message = ResultMessage.notFound();
			message.setText("Can't add user to account. Account not found.");
		}else{
			//save account for user
			user.setAccount(acct);
			message = this.save(user);
			message.setResult(user);
		}
		return message;
	}
	
	
	/**
	 * Save user role for user. 
	 * @param userId	{@link Integer} user id
	 * @param roleId	{@link Integer} role id
	 * @return			{@link ResultMessage}
	 */
	public ResultMessage addRole(Integer userId, Integer roleId){
		ResultMessage message;
		//make sure user and role exist
		User user = dao.get(userId);
		Role role = roleDAO.get(roleId);
		if(user == null){
			message = ResultMessage.notFound();
			message.setText("Can't add role to user. User not found.");
		}else if(role == null){
			message = ResultMessage.notFound();
			message.setText("Can't add role to user. Role not found.");
		}else{
			//save user role
			user.setRole(role);
			message = this.save(user);
			message.setResult(user);
		}
		return message;
	}
	
}
