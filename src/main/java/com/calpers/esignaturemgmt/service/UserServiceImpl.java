package com.calpers.esignaturemgmt.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.calpers.esignaturemgmt.model.Role;
import com.calpers.esignaturemgmt.model.User;
import com.calpers.esignaturemgmt.repository.UserRepository;
import com.calpers.esignaturemgmt.web.dto.UserRegistrationDto;
import com.calpers.esignaturemgmt.web.dto.UserSessionDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private HttpSession session;

	public static final int INTERNAL_USER = 1;

	public static final int EXTERNAL_USER = 2;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(UserRegistrationDto registration) {
		User user = new User();
		user.setFirstName(registration.getFirstName());
		user.setLastName(registration.getLastName());
		user.setEmail(registration.getEmail());
		user.setContactNo(registration.getContactNo());
		user.setOrganization(registration.getOrganization());
		user.setPassword(passwordEncoder.encode(registration.getPassword()));
		if (registration.getEmail().contains("@calpers.com")) {
			user.setUserType(INTERNAL_USER);
		} else {
			user.setUserType(EXTERNAL_USER);
		}

		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		} else if (!user.isEnabled()) {
			throw new UsernameNotFoundException("Account is not activated yet.");
		} else {
			UserSessionDto userObj = new UserSessionDto();
            userObj.copyToSessionDto(user);
			session.setAttribute("userDetails", userObj);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					mapRolesToAuthorities(user.getRoles()));
		}
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
