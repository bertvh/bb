package com.github.ginjaninja.bb.account.user;

import java.util.Collection;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;
import com.github.ginjaninja.bb.message.ResultMessage;
import com.github.ginjaninja.bb.service.ServiceInterface;

@Service
@Transactional
public class UserService implements ServiceInterface<User>{
	
	@Autowired
	private GenericDAOImpl<User> dao;

	@Override
	public ResultMessage get(Integer id) {
		User user = dao.get(id);
		if(user != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), user);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	@Override
	public ResultMessage save(User user) {
		ResultMessage message = null;
		try{
			if(user.getId() != null){
				user = dao.save(user);
			}else{
				user = dao.update(user);
			}
			message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), user);
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getLocalizedMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getLocalizedMessage());
		}
		return message;
	}

	@Override
	public ResultMessage delete(User user) {
		ResultMessage message = null;
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

	@Override
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<User> users = dao.getMany(queryName, params);
		if(users != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), users);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	@Override
	public ResultMessage getMany(String queryName) {
		Collection<User> users = dao.getMany(queryName);
		if(users != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), users);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	@Override
	public ResultMessage deactivate(User user) {
		user.setActiveInd("Y");
		return this.save(user);
	}

	@Override
	public ResultMessage activate(User user) {
		user.setActiveInd("N");
		return this.save(user);
	}

	
	
	
}
