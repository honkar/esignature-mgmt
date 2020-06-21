package com.calpers.esignaturemgmt.web.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.calpers.esignaturemgmt.constraint.FieldMatch;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
        //@FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserRegistrationDto {

    @NotEmpty (message = "Please enter first name")
    private String firstName;

    @NotEmpty (message = "Please enter last name")
    private String lastName;

    @NotEmpty (message = "Please enter password")
    private String password;

    @NotEmpty (message = "Please enter confirm password")
    private String confirmPassword;
    
    @NotEmpty (message = "Please enter contact no")
    private String contactNo;
    
    @NotEmpty (message = "Please enter organization")
    private String organization;

    @Email (message = "Please enter a valid e-mail address")
    @NotEmpty
    private String email;
    
//    //@Email
//    @NotEmpty
//    private String confirmEmail;

	@AssertTrue (message = "Please agree to the terms and conditions")
    private Boolean terms;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
//	public String getConfirmEmail() {
//		return confirmEmail;
//	}
//
//	public void setConfirmEmail(String confirmEmail) {
//		this.confirmEmail = confirmEmail;
//	}

}
