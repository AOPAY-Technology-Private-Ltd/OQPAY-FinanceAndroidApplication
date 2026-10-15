package com.bosandroidapp.oqmobilefinance.data.model.cibilscore;

import com.google.gson.annotations.SerializedName;

public class MatchResult{

	@SerializedName("Exact_match")
	private String exactMatch;

	public String getExactMatch(){
		return exactMatch;
	}
}