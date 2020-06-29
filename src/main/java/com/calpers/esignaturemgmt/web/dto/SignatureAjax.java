package com.calpers.esignaturemgmt.web.dto;

public class SignatureAjax {
	
	private String signImg;
	private String uploadType;
	private String preferredName;
	
	public String getSignImg() {
		return signImg;
	}
	public void setSignImg(String signImg) {
		this.signImg = signImg;
	}
	
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	
	public String getPreferredName() {
		return preferredName;
	}
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
}
