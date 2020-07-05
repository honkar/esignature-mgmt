package com.calpers.esignaturemgmt.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.UserRepository;
import com.calpers.esignaturemgmt.service.NotificationService;
import com.calpers.esignaturemgmt.service.UserService;
import com.calpers.esignaturemgmt.web.dto.EmailAjax;
import com.calpers.esignaturemgmt.web.dto.PwdAjax;
import com.calpers.esignaturemgmt.web.dto.PwdDto;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@RestController
public class PasswordMgmtController {

	private Logger logger = LoggerFactory.getLogger(PasswordMgmtController.class);

	@Autowired
	HttpSession session;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private NotificationService notifyService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * Change Password
	 * @param pwdObj
	 * @return
	 */
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody PwdAjax pwdObj) {
		UserSessionDto userObj = (UserSessionDto) session.getAttribute("userDetails");
		User user = userService.findByEmail(userObj.getEmail());
		if (user != null && passwordEncoder.matches(pwdObj.getCurrentPwd(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(pwdObj.getNewPwd()));
			User updatedUser = userRepository.save(user);
			if (updatedUser != null) {
				return new ResponseEntity<>("Success", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
	}

	
	/**
	 * Reset Password
	 * @param emailObj
	 * @return
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	public ResponseEntity<Object> resetPassword(@RequestBody EmailAjax emailObj) {
		User existing = userService.findByEmail(emailObj.getEmail());
		if (existing != null) {
			try {
				notifyService.resetPassword(existing);
			} catch (Exception e) {

				logger.info("Error while sending email " + e.getMessage());
			}
			return new ResponseEntity<>("Success", HttpStatus.OK);
		}

		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Reset Password
	 * @param pwdDto
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	public ResponseEntity<Object> resetPassword(@RequestBody PwdDto pwdDto, BindingResult result) {
		User user = userService.findByEmail(pwdDto.getEmail());
		if (user != null) {
			user.setPassword(passwordEncoder.encode(pwdDto.getNewPwd()));
			User updatedUser = userRepository.save(user);
			if (updatedUser != null) {

				return new ResponseEntity<>("Success", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
	}

}
