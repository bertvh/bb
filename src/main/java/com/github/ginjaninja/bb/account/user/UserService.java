package com.github.ginjaninja.bb.account.user;

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
public class UserService implements ServiceInterface<User>{
	final static Logger LOG = LoggerFactory.getLogger("UserService");
	
	@Autowired
	private UserDAO dao;
	
	public UserService(){
		
	}

	/**
	 * Get user by id
	 * @param 	id {@link Integer}
	 * @return 	{@link ResultMessage}
	 */
	@Override
	public ResultMessage get(Integer id) {
		User user = dao.get(id);
		if(user != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), user);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	/**
	 * Save user 
	 * @param user {@link User}
	 * @return {@link ResultMessage}
	 */
	@Override
	public ResultMessage save(User user) {
		ResultMessage message = null;
		this.setDefaults(user);
		try{
			if(user.getId() == null){
				user = dao.save(user);
			}else{
				//set createDtTm from existing record
				User storedUser = dao.get(user.getId());
				if(storedUser == null){
					throw new IllegalArgumentException("No user exists with that id");
				}
				user.fillFields(dao);
				user.setCreatedDtTm(storedUser.getCreatedDtTm());
				user = dao.update(user);
			}
			message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), user);
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
		}
		return message;
	}

	/**
	 * Delete user by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	@Override
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		User user = dao.get(id);
		try{
			dao.delete(user);
			message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString());
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getLocalizedMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getLocalizedMessage());
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
		Collection<User> users = dao.getMany(queryName, params);
		if(users != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), users);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	/**
	 * Get many with named query
	 * @param queryName {@link String}
	 * @return			{@link ResultMessage}
	 */
	@Override
	public ResultMessage getMany(String queryName) {
		Collection<User> users = dao.getMany(queryName);
		if(users != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), users);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	/**
	 * Deactivate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage deactivate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("Y");
		return this.save(user);
	}

	/**
	 * Activate user by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	@Override
	public ResultMessage activate(Integer id) {
		User user = dao.get(id);
		user.setActiveInd("N");
		return this.save(user);
	}

	/**
	 * Sets active (if null) and activity date/time
	 * @param user	{@link User}
	 * @return		{@link User}
	 */
	private User setDefaults(User user){
		if(user.getActiveInd() == null){
			user.setActiveInd("Y");
		}
		user.setActivityDtTm(new Date());
		return user;
	}
	
	private User fill(User user){
		
		return user;
	}
}
