package com.github.ginjaninja.bb.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceException;

import com.github.ginjaninja.bb.dao.GenericDAO;
import com.github.ginjaninja.bb.domain.DomainObject;
import com.github.ginjaninja.bb.message.ResultMessage;

public class GenericService {
	
	
	/**
	 * Get user by id
	 * @param 	id {@link Integer}
	 * @return 	{@link ResultMessage}
	 */
	
	public ResultMessage get(GenericDAO dao, Integer id) {
		DomainObject o = (DomainObject) dao.get(id);
		if(o != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.valueOf("NOT_FOUND").toString());
		}
	}

	/**
	 * Save t 
	 * @param t {@link DomainObject}
	 * @return {@link ResultMessage}
	 */
	
	public ResultMessage save(GenericDAO dao, DomainObject o) {
		ResultMessage message = null;
		try{
			if(o.getId() == null){
				//set defaults
				o.fillFields();
				String result = o.checkRequired();
				//save if checkRequired message is empty
				if(result.length() == 0){
					o = (DomainObject) dao.save(o);
					message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
				}else{
					//return error with missing properties
					message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.MISSING_PROPERTIES.toString(), result);
				}
			}else{
				//get stored t
				DomainObject storedO = (DomainObject) dao.get(o.getId());
				if(storedO == null){
					throw new IllegalArgumentException(ResultMessage.Msg.NOT_FOUND.toString());
				}
				//fill in null fields from stored object
				o.fillFields(storedO);
				//update activity date time
				o.setActivityDtTm(new Date());
				o = (DomainObject) dao.update(o);
				message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
			}
		}catch(PersistenceException pe){
			message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
		}catch(Exception e){
			message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
		}
		return message;
	}

	/**
	 * Delete t by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	
	public ResultMessage delete(GenericDAO dao, Integer id) {
		ResultMessage message = null;
		DomainObject o = (DomainObject) dao.get(id);
		if(o == null){
			message = new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}else{
			try{
				dao.delete(o);
				message = new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString());
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
	
	public ResultMessage getMany(GenericDAO dao, String queryName, Map<String, Object> params) {
		Collection<DomainObject> o = dao.getMany(queryName, params);
		if(o != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	/**
	 * Get many with named query
	 * @param queryName {@link String}
	 * @return			{@link ResultMessage}
	 */
	
	public ResultMessage getMany(GenericDAO dao, String queryName) {
		Collection<DomainObject> o = dao.getMany(queryName);
		if(o != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.NOT_FOUND.toString());
		}
	}

	/**
	 * Deactivate t by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	
	public ResultMessage deactivate(GenericDAO dao, Integer id) {
		DomainObject o = (DomainObject) dao.get(id);
		o.setActiveInd("N");
		return this.save(dao, o);
	}

	/**
	 * Activate t by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage activate(GenericDAO dao, Integer id) {
		DomainObject o = (DomainObject) dao.get(id);
		o.setActiveInd("Y");
		return this.save(dao, o);
	}
}
