package com.github.ginjaninja.bb.account.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.message.ResultMessage;

@Service
@Transactional
public class AccountService {
	@Autowired
	private AccountDAO dao;
	
	/**
	 * Get account by id
	 * @param 	id 	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage get(Integer id) {
		Account account = dao.get(id);
		if(account != null){
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.convert(account);
			return ResultMessage.success(accountDTO);
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Save account 
	 * @param account 	{@link Account}
	 * @return 			{@link ResultMessage}
	 */
	public ResultMessage save(Account account) {
		ResultMessage message = null;
		if(account.getId() == null){
			//set defaults
			account.fillFields();
			String result = account.checkRequired();
			//save if checkRequired message is empty
			if(result.length() == 0){
				account = dao.save(account);
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.convert(account);
				message = ResultMessage.success(accountDTO);
			}else{
				//return error with missing properties
				message = ResultMessage.missingProperties(result);
			}
		}else{
			//get stored account
			Account storedAccount = dao.get(account.getId());
			if(storedAccount == null){
				message = ResultMessage.notFound();
			}else{
				//fill in not nullable fields from stored object
				account.fillFields(storedAccount);
				//update activity date time
				account.setActivityDtTm(new Date());
				account = dao.update(account);
				AccountDTO accountDTO = new AccountDTO();
				accountDTO.convert(account);
				message = ResultMessage.success(accountDTO);
			}
		}
		return message;
	}

	/**
	 * Delete account by id
	 * @param id	{@link Integer}
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage delete(Integer id) {
		ResultMessage message = null;
		Account account = dao.get(id);
		if(account == null){
			message = ResultMessage.notFound();
		}else{
			try{
				dao.delete(account);
				message = ResultMessage.success();
			}catch(PersistenceException pe){
				message = new ResultMessage(ResultMessage.Type.ERROR, pe.getMessage());
			}catch(Exception e){
				message = new ResultMessage(ResultMessage.Type.ERROR, e.getMessage());
			}
		}
		return message;
	}
	
	/**
	 * Convert collection of Accounts to AccountDTOs
	 * @param accounts	Collection<Account>
	 * @return		Collection<AccountDTO>
	 */
	private Collection<AccountDTO> convertMany(Collection<Account> accounts){
		Collection<AccountDTO> dtoAccounts = new ArrayList<AccountDTO>();
		AccountDTO dto;
		for(Account u : accounts){
			dto = new AccountDTO();
			dto.convert(u);
			dtoAccounts.add(dto);
		}
		return dtoAccounts;
	}
	
	/**
	 * Get many with named query and params
	 * @param queryName {@link String}
	 * @param params	Map<String, Object>
	 * @return			{@link ResultMessage}
	 */
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		Collection<Account> accounts = dao.getMany(queryName, params);
		if(accounts != null){
			return ResultMessage.success(this.convertMany(accounts));
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
		Collection<Account> accounts = dao.getMany(queryName);
		if(accounts != null){
			return ResultMessage.success(this.convertMany(accounts));
		}else{
			return ResultMessage.notFound();
		}
	}

	/**
	 * Deactivate account by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage deactivate(Integer id) {
		Account account = dao.get(id);
		account.setActiveInd("N");
		return this.save(account);
	}

	/**
	 * Activate account by id
	 * @param id	{@link Integer}
	 * @return		{@link ResultMessage}
	 */
	public ResultMessage activate(Integer id) {
		Account account = dao.get(id);
		account.setActiveInd("Y");
		return this.save(account);
	}

}
