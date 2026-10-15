package com.bosandroidapp.oqmobilefinance.data.model.cibilscore;

import com.google.gson.annotations.SerializedName;

public class UserMessage{

	@SerializedName("UserMessageText")
	private String userMessageText;

	public String getUserMessageText(){
		return userMessageText;
	}
}