package com.kittipong.assignment.feature.user.service;


import com.kittipong.assignment.common.config.JwtTokenUtil;
import com.kittipong.assignment.common.datasource.entities.UserEntity;
import com.kittipong.assignment.common.datasource.repo.UserRepository;
import com.kittipong.assignment.feature.order.service.OrderService;
import com.kittipong.assignment.feature.user.controller.domain.AuthenRequest;
import com.kittipong.assignment.feature.user.controller.domain.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OrderService orderService;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = getUser(username);
		return new User(user.getUserName(), user.getPassword(),
				new ArrayList<>());
	}
	
	public void save(UserRequest user) {
		UserEntity newUser = new UserEntity();
		newUser.setUserName(user.getUserName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setName(user.getName());
		newUser.setSurname(user.getSurname());
		newUser.setDateOfBirth(user.getDateOfBirth());
		userRepository.save(newUser);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String authHeader) {
		String user = getUserFromHeader(authHeader);
		userRepository.deleteByUserName(user);
		orderService.delete(user);
	}

	public String  getUserFromHeader(String authHeader){
		String jwtToken = authHeader.substring(7);
		return jwtTokenUtil.getUsernameFromToken(jwtToken);
	}

	private void authenticate(String username, String password) throws DisabledException,BadCredentialsException {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	public String login(AuthenRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());

		return jwtTokenUtil.generateToken(userDetails);
	}

	public UserEntity getUser(String username){
		UserEntity user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return user;
	}
}