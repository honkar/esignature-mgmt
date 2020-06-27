package com.calpers.esignaturemgmt.web.dto;

import com.calpers.esignaturemgmt.model.User;

public class UserSessionDto {
	
	private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String contactNo;
    private int userType;
    private boolean enabled;
    private boolean signStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isSignStatus() {
		return signStatus;
	}
	public void setSignStatus(boolean signStatus) {
		this.signStatus = signStatus;
	}
    
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public void copyToSessionDto(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.contactNo = user.getContactNo();
		this.organization = user.getOrganization();
        this.userType = user.getUserType();
		this.enabled = user.isEnabled();
		this.signStatus = user.isSignStatus();
		
	}
	public void copyFromSessionDto(User user) {
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setContactNo(this.contactNo);
		user.setOrganization(this.organization);
	}
    
    
}
