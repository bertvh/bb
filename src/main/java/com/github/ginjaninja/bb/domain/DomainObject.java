package com.github.ginjaninja.bb.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class DomainObject {
	
    
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
