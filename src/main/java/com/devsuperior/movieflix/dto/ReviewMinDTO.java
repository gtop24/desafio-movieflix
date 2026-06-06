package com.devsuperior.movieflix.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "text", "userName" })
public class ReviewMinDTO {

	private String text;
	private String userName;

	public ReviewMinDTO() {
	}

	public ReviewMinDTO(String text, String userName) {
		this.text = text;
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
