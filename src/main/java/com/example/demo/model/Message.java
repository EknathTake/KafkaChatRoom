package com.example.demo.model;


/**
 * @author eknathtake
 */
public class Message {

	private Double uuid;
	private String senderNumber;
	private String recepientNumber;
	private String messageBody;
	private String key;
	//private MultipartFile file;

	public Double getUuid() {
		return uuid;
	}
	
	public void setUuid(Double uuid) {
		this.uuid = uuid;
	}
	
	public String getSenderNumber() {
		return senderNumber;
	}

	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}

	public String getRecepientNumber() {
		return recepientNumber;
	}

	public void setRecepientNumber(String recepientNumber) {
		this.recepientNumber = recepientNumber;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
//
//	public MultipartFile getFile() {
//		return file;
//	}
//
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [senderNumber=");
		builder.append(senderNumber);
		builder.append(", recepientNumber=");
		builder.append(recepientNumber);
		builder.append(", messageBody=");
		builder.append(messageBody);
		builder.append(", key=");
		builder.append(key);
		//builder.append(", file=");
		//builder.append(file);
		builder.append("]");
		return builder.toString();
	}

}
