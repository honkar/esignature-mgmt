package com.calpers.esignaturemgmt.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.calpers.esignaturemgmt.model.ConfirmationToken;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.ConfirmationTokenRepository;
import com.calpers.esignaturemgmt.repository.UserRepository;
import com.calpers.esignaturemgmt.service.NotificationService;
import com.calpers.esignaturemgmt.service.UserService;
import com.calpers.esignaturemgmt.web.dto.PwdDto;
import com.calpers.esignaturemgmt.web.dto.UserRegistrationDto;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@Controller
public class UserRegistrationController {

	private Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private NotificationService notifyService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@RequestMapping(value = "/registration", method = { RequestMethod.GET })
	public String showRegistrationForm(Model model) {
		return "signup";
	}

	@RequestMapping(value = "/registration", method = { RequestMethod.POST })
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
			BindingResult result) {

		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "signup";
		}

		User registeredUser = userService.save(userDto);
		// send confirmation email at this point
		try {
			notifyService.sendNotification(registeredUser);
		} catch (Exception e) {

			logger.info("Error while sending email " + e.getMessage());
		}
		return "registerSuccess";
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userService.findByEmail(token.getUser().getEmail());
			user.setEnabled(true);
			userRepository.save(user);
			modelAndView.addObject("title", "Account Activation");
			modelAndView.addObject("message", "Account activated");
			modelAndView.setViewName("accountActivation");
		} else {
			modelAndView.addObject("title", "Account Activation");
			modelAndView.addObject("message", "The link is invalid or broken!");
			modelAndView.setViewName("accountActivation");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/resetpassword", method = { RequestMethod.GET })
	public String forgotPassword(Model model, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		if (token != null) {
			String email = token.getUser().getEmail();
			model.addAttribute("email",email);
		} else {
			model.addAttribute("error", "The link is invalid or broken!");
		}
		return "resetpwd";
	}

}
