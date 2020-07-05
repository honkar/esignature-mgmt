package com.calpers.esignaturemgmt.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.UserRepository;
import com.calpers.esignaturemgmt.service.UserService;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@Controller
public class ProfileController {

	@Autowired
	private HttpSession session;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/viewprofile", method = { RequestMethod.GET })
	public String viewprofile(Model model) {
		return "calpers_Viewprofile";
	}

	/**
	 * Edit Profile
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editprofile", method = { RequestMethod.GET })
	public String editProfile(Model model) {
		UserSessionDto user = (UserSessionDto) session.getAttribute("userDetails");
		model.addAttribute("editUserObj", user);
		return "calpers_editprofile";
	}

	/**
	 * Save Profile
	 * @param userDto
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/editprofile", method = { RequestMethod.POST })
	public String saveProfile(@ModelAttribute("editUserObj") @Valid UserSessionDto userDto, BindingResult result) {
		UserSessionDto userObj = (UserSessionDto) session.getAttribute("userDetails");
		User user = userService.findByEmail(userObj.getEmail());
		userDto.copyFromSessionDto(user);

		User updatedUser = userRepository.save(user);
		if (updatedUser != null) {
			session.removeAttribute("userDetails");
			UserSessionDto userSession = new UserSessionDto();
			userSession.copyToSessionDto(updatedUser);
			session.setAttribute("userDetails", userSession);
			return "redirect:/viewprofile?success";
		}
		return "redirect:/viewprofile?error";
		
	}

}
