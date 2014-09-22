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
		UNKNOWN_ERROR("Unknown error occurred."),
		NOT_FOUND("Entity with id not found."),
		BAD_JSON("Poorly formed JSON object."),
		TYPE_MISMATCH("Type mismatch. Please check your request."),
		MISSING_PROPERTIES("Missing required properties."),
		HAS_CHILDREN("Cannot delete entity with active children.");
		
		private String message;
		private Msg(String message){
			this.message = message;
		}
		
		@Override
		public String toString(){
			return this.message;
		}
	}
	
	private String text;
	private Type type;
	private Object result;
	
	/**
	 * Construct message without result object;
	 * @param type 	{@link Type} enum
	 * @param text 	{@link String} message text
	 * @return 		{@link ResultMessage}
	 */
	public ResultMessage(Type type, String text){
		this.type = type;
		this.text = text;
		this.result = null;
	}
	
	/**
     * Construct message with result object;
     * @param type 		{@link Type} enum
     * @param text 		{@link String} message text
     * @param result	{@link Object} result object
     * @return 			{@link ResultMessage} 
     */
	public ResultMessage(Type type, String text, Object result){
		this.type = type;
		this.text = text;
		this.result = result;
	}

	/**
	 * Return Success message when transaction completed successfully
	 * @param o	{@link Object} 
	 * @return	{@link ResultMessage}
	 */
	public static ResultMessage success(Object o){
		return new ResultMessage(ResultMessage.Type.SUCCESS, ResultMessage.Msg.OK.toString(), o);
	}
	
	/**
	 * Return entity Not Found message (used when cannot locate entity with id for update/fetch/delete)
	 * @return	{@link ResultMessage}
	 */
	public static ResultMessage notFound(){
		return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.valueOf("NOT_FOUND").toString());
	}
	
	/**
	 * Return Missing Properties message (used for save/update)
	 * @param propertyString	{@link String} of missing properties
	 * @return					{@link ResultMessage}
	 */
	public static ResultMessage missingProperties(String propertyString){
		return new ResultMessage(ResultMessage.Type.ERROR, ResultMessage.Msg.MISSING_PROPERTIES.toString(), propertyString);
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
