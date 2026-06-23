package com.bosandroidapp.oqmobilefinance.data.model.cibilscore;

import com.google.gson.annotations.SerializedName;

public class NonCreditCAPS{

	@SerializedName("NonCreditCAPS_Summary")
	private NonCreditCAPSSummary nonCreditCAPSSummary;

	public NonCreditCAPSSummary getNonCreditCAPSSummary(){
		return nonCreditCAPSSummary;
	}
}