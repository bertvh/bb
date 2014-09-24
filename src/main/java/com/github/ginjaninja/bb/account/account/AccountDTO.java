/** Account object with fewer fields for responding to API requests. **/
package com.github.ginjaninja.bb.account.account;

import java.util.Date;

import com.github.ginjaninja.bb.domain.DomainDTO;

public class AccountDTO extends DomainDTO{
	private Integer id;
	
	/** name of account **/
	private String name;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    private String activeInd;
     
    /** Date/Time last updated **/
    private Date activityDtTm;

    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public Date getActivityDtTm() {
		return activityDtTm;
	}

	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

    
}
