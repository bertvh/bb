package com.github.ginjaninja.bb.service;

import java.util.Map;

import com.github.ginjaninja.bb.message.ResultMessage;

public interface ServiceInterface <T>{
	public ResultMessage get(Integer id);
	public ResultMessage save(T t);
	public ResultMessage delete(T t);
	public ResultMessage deactivate(T t);
	public ResultMessage activate(T t);
	public ResultMessage getMany(String queryName, Map<String, Object> params);
	public ResultMessage getMany(String queryName);
}
