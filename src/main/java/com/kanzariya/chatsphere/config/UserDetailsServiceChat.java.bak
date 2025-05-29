package com.kanzariya.chatsphere.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceChat implements UserDetailsService {

	private final UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = "";
		String role = "";
		List<Object[]> result = usersRepository.findPasswordAndRoleByEmailOrMobile(username);
		for (Object[] row : result) {
			password = (String) row[0];
			role = (String) row[1];

		}
		List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(role));

		return new User(username, password, authorityList);
	}

}
