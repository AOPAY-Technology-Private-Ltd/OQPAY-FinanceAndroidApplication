package com.bosandroidapp.oqmobilefinance.data.model.cibilscore;

import com.google.gson.annotations.SerializedName;

public class ResultJson{

	@SerializedName("INProfileResponse")
	private INProfileResponse iNProfileResponse;

	public INProfileResponse getINProfileResponse(){
		return iNProfileResponse;
	}
}