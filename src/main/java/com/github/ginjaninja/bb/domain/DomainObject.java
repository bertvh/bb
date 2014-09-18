package com.github.ginjaninja.bb.domain;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

public abstract class DomainObject {
	
	private Integer id;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	//TODO remove outputs
	//TODO add comments
	@SuppressWarnings("rawtypes")
	public void fillFields(GenericDAOImpl dao) throws NoSuchFieldException, SecurityException, IllegalArgumentException, ReflectiveOperationException{
		Object o = dao.get(this.getId());
		
		//for each field in object
		System.out.println("field size "+ this.getClass().getDeclaredFields().length);
		for(Field field : this.getClass().getDeclaredFields()){
			System.out.println("field name "+field.getName());
			field.setAccessible(true);
			//if value field in this is null
			if(field.get(this) == null){
				//get @column annotation
				Column columnAnnotation = field.getAnnotation(Column.class);
				//if @column annotation exists and field is not nullable
				if(columnAnnotation != null && !columnAnnotation.nullable()){
					System.out.println("column name " +columnAnnotation.name());
					//get field from stored object set value in this object
					Field oField = o.getClass().getDeclaredField(field.getName());
					oField.setAccessible(true);
					field.set(this, oField.get(o));		
				}
							
			}
			
		}
	}

}