package com.github.ginjaninja.bb.domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DomainDTO {
	private static final Logger LOG = LoggerFactory.getLogger("DomainDTO");
	
	/**
	 * Use reflection to convert domain object to DTO 
	 * @param o {@link DomainObject}
	 */
	public void convert(DomainObject o) {
		if(null != o){
			//get declared fields for o
			Field[] oFields = o.getClass().getDeclaredFields();
			Map<String, Field> oFieldsMap = new HashMap<>();
			for(Field of : oFields){
				oFieldsMap.put(of.getName(), of);
			}
			
			for(Field field : this.getClass().getDeclaredFields()){
	    		//make private field accessible
	    		field.setAccessible(true);
	    		//if o has this field 
	    		try {
	    			if(oFieldsMap.containsKey(field.getName())){
	    				//get field from o
						Field oField = o.getClass().getDeclaredField(field.getName());
						//make private field accessible
						oField.setAccessible(true);
						//set dto field value from o
						field.set(this, oField.get(o));
					}
				} catch (NoSuchFieldException | SecurityException
						| IllegalArgumentException | IllegalAccessException e) {
					LOG.error("Error converting DomainObject to DomainObjectDTO", e);
				}
			}
		}
	}
	 
	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
