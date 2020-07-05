package com.calpers.esignaturemgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.calpers.esignaturemgmt.model.ConfirmationToken;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.ConfirmationTokenRepository;

@Service
public class NotificationService {
	public static final int TOKEN_TYPE_REGISTRATION = 1;
	public static final int TOKEN_TYPE_RESETPWD  = 2;
	
	private JavaMailSender mailSender;
	
	 @Autowired
	 private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	public NotificationService(JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	
	/**
	 * Send account confirmation notification
	 * @param user
	 * @throws MailException
	 */
	public void sendNotification(User user) throws MailException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user, TOKEN_TYPE_REGISTRATION);
        confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("noreply.esignmgmt@gmail.com");
		mail.setSubject("CalPERS eSignature Management : Account Activation");
		mail.setText("To confirm your account, please click here : "
	            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
		mailSender.send(mail);
		
	}
	
	/**
	 * Send reset password email
	 * @param user
	 * @throws MailException
	 */
	public void resetPassword(User user) throws MailException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user,TOKEN_TYPE_RESETPWD);
        confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("noreply.esignmgmt@gmail.com");
		mail.setSubject("CalPERS eSignature Management : Reset Password");
		mail.setText("Please link on the below link to reset your eSignature Management account password: "
	            +"http://localhost:8080/resetpassword?token="+confirmationToken.getConfirmationToken());
		mailSender.send(mail);
		
	}
	
	

}
