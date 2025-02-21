package com.arcis.vgil.model;

import java.io.Serializable;

public class AmNameModel implements Serializable {
	
	private String amidd;
	public String getAmidd() {
		return amidd;
	}
	public void setAmidd(String amidd) {
		this.amidd = amidd;
	}
	public String getAmname() {
		return amname;
	}
	public void setAmname(String amname) {
		this.amname = amname;
	}
	private String amname;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	private String Message;

}
