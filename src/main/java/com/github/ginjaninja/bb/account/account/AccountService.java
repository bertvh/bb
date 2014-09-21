package com.github.ginjaninja.bb.account.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.message.ResultMessage;
import com.github.ginjaninja.bb.service.ServiceInterface;

@Service
@Transactional
public class AccountService implements ServiceInterface<Account>{
	@Autowired
	private AccountDAO dao;
	
	/**
	 * Get account by id
	 * @param 	id {@link Integer}
	 * @return 	{@link ResultMessage}
	 */
	@Override
	public ResultMessage get(Integer id) {
		Account account = dao.get(id);
		if(account != null){
			return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), account);
		}else{
			return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.valueOf("NOT_FOUND").toString());
		}
	}

	@Override
	public ResultMessage save(Account t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage delete(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage deactivate(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage activate(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage getMany(String queryName, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMessage getMany(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

}
