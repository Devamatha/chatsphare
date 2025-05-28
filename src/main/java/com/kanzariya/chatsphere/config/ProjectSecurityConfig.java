package com.kanzariya.chatsphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll();

		http.csrf(crsf -> crsf.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers("/api/auth/register","/api/auth/login").permitAll());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
	
	
//	@Bean
//	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
//			PasswordEncoder passwordEncoder) {
//		UserNamePwdAuthenticationProvider authenticationProvider = new UserNamePwdAuthenticationProvider(
//				userDetailsService, passwordEncoder);
//		ProviderManager providerManager = new ProviderManager(authenticationProvider);
//		providerManager.setEraseCredentialsAfterAuthentication(false);
//		return providerManager;
//	}
}