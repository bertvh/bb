package com.github.ginjaninja.bb.message;


/**
 * Message builder for ResponseEntity<ResultMessage>. Returns formatted message converted to json via @ResponseBody.
 *
 */
public class ResultMessage {
	/**
	 * Types of messages
	 */
	public static enum Type {
        ERROR, WARNING, INFO, SUCCESS;
	}
	
	/**
	 * Some message presets
	 */
	public static enum Msg {
		OK("OK"), 
		UNKNOWN_ERROR("Unknown error occurred"),
		NOT_FOUND("Entity with id not found"),
		BAD_JSON("Poorly formed JSON object");
		
		private String value;
		private Msg(String value){
			this.value = value;
		}
	}
	
	private String text;
	private Type type;
	private Object result;
	
	/**
	 * Construct message without result object;
	 * @param type Type
	 * @param text String
	 * @return Message 
	 */
	public ResultMessage(Type type, String text){
		this.type = type;
		this.text = text;
		this.result = null;
	}
	
	/**
     * Construct message with result object;
     * @param type Type
     * @param text String
     * @param result Object
     * @return Message 
     */
	public ResultMessage(Type type, String text, Object result){
		this.type = type;
		this.text = text;
		this.result = result;
	}

	public ResultMessage() {
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Type getType() {
		return type;
	}

	public Object getResult() {
		return result;
	}
	
}
