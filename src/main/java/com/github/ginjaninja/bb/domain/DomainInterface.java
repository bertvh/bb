package com.github.ginjaninja.bb.domain;

import java.util.Date;

public interface DomainInterface {
	public Integer getId();
	public void setId(Integer id);
	
	public String getActiveInd();
	public void setActiveInd(String active);
	
	public Date getActivityDtTm();
	public void setActivityDtTm(Date date);
	
	public Date getCreatedDtTm();
	public void setCreatedDtTm(Date date);
}
