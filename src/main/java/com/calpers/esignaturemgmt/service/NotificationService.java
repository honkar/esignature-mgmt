package com.calpers.esignaturemgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.calpers.esignaturemgmt.model.ConfirmationToken;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.ConfirmationTokenRepository;
import com.calpers.esignaturemgmt.web.dto.UserRegistrationDto;

@Service
public class NotificationService {
	
	private JavaMailSender mailSender;
	
	 @Autowired
	 private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	public NotificationService(JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	
	public void sendNotification(User user) throws MailException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom("noreply.esignmgmt@gmail.com");
		mail.setSubject("CalPERS eSignature Management : Account Activation");
		mail.setText("To confirm your account, please click here : "
	            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
		mailSender.send(mail);
		
	}
	
	

}
