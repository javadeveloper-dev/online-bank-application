package com.banking_app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.banking_app.service.impl.CustomUserDetailService;

import jakarta.servlet.DispatcherType;
@Configuration
@EnableWebSecurity
public class OnlineBankApplicationSecurityConfig {
	
	private final CustomUserDetailService customUserDetailService;
	
	public OnlineBankApplicationSecurityConfig(CustomUserDetailService customUserDetailService) {
		this.customUserDetailService = customUserDetailService;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(customUserDetailService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	
	
//	SecurityFilterChain  for Admin
	
	@Bean
	@Order(1)
	SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/onlinebankapplication/admin/**","/onlinebankapplication/logout");
		http.authorizeHttpRequests(
				(requests) -> requests.
				dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
				.requestMatchers("/onlinebankapplication/admin/loadAdminRegister",
					"onlinebankapplication/login/adminLogin","/onlinebankapplication/admin/saveAdminRegistrationData", 
						"/error").permitAll().anyRequest().hasRole("ADMIN"))
				
		        .formLogin(form -> form.loginPage("/onlinebankapplication/welcome")
						.loginProcessingUrl("/onlinebankapplication/login/adminLogin")
						.defaultSuccessUrl("/onlinebankapplication/admin/dashboard", true)
						.failureUrl("/onlinebankapplication/admin/login?error=true"))
						
				.logout(logout -> logout.logoutUrl("/onlinebankapplication/logout")
								.logoutSuccessUrl("/onlinebankapplication/welcome").invalidateHttpSession(true)
								.deleteCookies("JSESSIONID"));
				
		
		return http.build();
	}
	
	
	@Bean
	@Order(2)
	public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/onlinebankapplication/user/**");
		http.authorizeHttpRequests(
				(requests) -> requests
					.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
					.requestMatchers("/onlinebankapplication/login/userLogin",
						"/onlinebankapplication/user/loadUserRegister","/onlinebankapplication/user/saveUserRegistrationData","isEmailAlreadyExists","isPasswordExistsOrNot").permitAll().anyRequest().hasRole("USER"))
				
		        .formLogin(form -> form.loginPage("/onlinebankapplication/login/userLogin")
						.loginProcessingUrl("/onlinebankapplication/login/userLogin")
						.defaultSuccessUrl("/onlinebankapplication/user/dashboard", true)
						.failureUrl("/user/login?error=true"))
						
				.logout(logout -> logout.logoutUrl("/onlinebankapplication/logout")
								.logoutSuccessUrl("/onlinebankapplication/welcome").invalidateHttpSession(true)
								.deleteCookies("JSESSIONID"));

		
		return http.build();
	}
	
	@Bean
	@Order(3)
	SecurityFilterChain defaultChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/error").authenticated() 
	            .anyRequest().permitAll()
	        );
	    return http.build();
	}

}