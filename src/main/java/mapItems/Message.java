package mapItems;

public class Message {
	private DataType type;
	private String msg;
	private Object o;
	
	/**
	 * This object represents a transmission from one object that implements a mapItems.CommInterface and another.
	 * This is designed to be very easy for a user to implement despite a low level of programming experience.
	 * @param msg This string is a keyword that helps the recipient understand how to interpret the message.
	 * @param type This is an indicator of what kind of datatype the object should be cast to.
	 * @param o This could be almost any data.
	 */
	public Message(String msg, DataType type, Object o) {
		this.type = type;
		this.msg = msg;
		this.o = o;
	}

	/**
	 * Gets the dataType of the message object.
	 * @return The dataType of the message object.
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * Sets the dataType of the message object.
	 */
	public void setType(DataType type) {
		this.type = type;
	}

	/**
	 * Gets the message String.
	 * @return The String keyword/phrase of the message.
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the msg String keyword/phrase of this message.
	 * @param msg Sets String keyword/phrase of this message.
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets the message object.
	 * @return o The object of the message.
	 */
	public Object getO() {
		return o;
	}

	/**
	 * Sets the message object.
	 */
	public void setO(Object o) {
		this.o = o;
	}
	
	
}
