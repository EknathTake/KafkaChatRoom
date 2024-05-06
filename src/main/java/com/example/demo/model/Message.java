package com.example.demo.model;

public class Message {

	private String senderNumber;
	private String recepientNumber;
	private String messageBody;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [senderNumber=");
		builder.append(senderNumber);
		builder.append(", recepientNumber=");
		builder.append(recepientNumber);
		builder.append(", messageBody=");
		builder.append(messageBody);
		builder.append("]");
		return builder.toString();
	}

}
