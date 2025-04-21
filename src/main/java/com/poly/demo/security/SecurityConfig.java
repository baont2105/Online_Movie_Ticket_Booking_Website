package com.poly.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.poly.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**")
				.hasRole("ADMIN")
				.requestMatchers("/login/**", "/register/**", "/forgot-password/**", "/movies/**", "/movie-detail/**",
						"/promotions/**", "/css/**", "/js/**", "/image/**", "/images/**", "/bootstrap-5.3.3-dist/**",
						"/fonts/**", "/assets/**", "/", "/oauth2/**", "/login/oauth2/**", "/check-login")
				.permitAll().anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home", true)
						.failureHandler(authenticationFailureHandler()).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll());

		// Đăng nhập mạng xã hội
		http.oauth2Login().loginPage("/login").defaultSuccessUrl("/oauth2/login/success", true)
				.failureUrl("/auth/login/error").authorizationEndpoint().baseUri("/oauth2/authorization");
		return http.build();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return (request, response, exception) -> {
			String errorMessage = "Đăng nhập thất bại.";
			if (exception instanceof DisabledException) {
				errorMessage = "Tài khoản của bạn đã bị khóa.";
				System.out.println("DisabledException: " + exception.getMessage());
			} else if (exception instanceof LockedException) {
				errorMessage = "Tài khoản của bạn đã bị khóa.";
				System.out.println("LockedException: " + exception.getMessage());
			} else if (exception instanceof BadCredentialsException) {
				errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng.";
				System.out.println("BadCredentialsException: " + exception.getMessage());
			} else {
				System.out.println("Lỗi khác: " + exception.getClass() + " - " + exception.getMessage());
			}
			request.getSession().setAttribute("error", errorMessage);
			response.sendRedirect("/login?error=true");
		};
	}
}
