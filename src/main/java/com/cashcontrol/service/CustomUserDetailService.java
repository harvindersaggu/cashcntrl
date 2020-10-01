
package com.cashcontrol.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cashcontrol.entity.Authority;
import com.cashcontrol.entity.Users;
import com.cashcontrol.repository.UsersRepository;

@Service("customUserDetailsService")
public class CustomUserDetailService implements UserDetailsService {

	private UsersRepository userRepository;

	@Autowired
	public CustomUserDetailService(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username).get();

		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else { // List<String> userRoles =
			//userRolesRepository.findRoleByUserName(username);

			List<Authority> roles = user.getRoles();
			Set<SimpleGrantedAuthority> rolenames = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getAuthority())).collect(Collectors.toSet());
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rolenames);
			//return new CustomUserDetails(user, rolenames);
		}
	}
}
