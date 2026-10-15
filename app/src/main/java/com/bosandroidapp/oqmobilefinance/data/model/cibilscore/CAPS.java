package com.bosandroidapp.oqmobilefinance.data.model.cibilscore;

import com.google.gson.annotations.SerializedName;

public class CAPS{

	@SerializedName("CAPS_Summary")
	private CAPSSummary cAPSSummary;

	public CAPSSummary getCAPSSummary(){
		return cAPSSummary;
	}
}